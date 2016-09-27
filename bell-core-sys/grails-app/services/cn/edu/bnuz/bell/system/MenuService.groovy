package cn.edu.bnuz.bell.system

/**
 * 菜单服务
 * @author Yang Lin
 */
class MenuService {
    def getUserMenus(String root, String userId, String userName, List<String> roles, boolean chinese) {
        def menus = []
        def stack = []
        def level = 1
        def map = [:]
        def curr

        // construct menu tree
        getMenusByRoot(root).each {
            def item = [
                    label: it.id == 'user.profile' ? userName : (chinese ? it.labelCn : it.labelEn),
                    items: []
            ]
            if(it.menuLevel == 1) {
                menus << item
            } else {
                if(it.menuLevel > level) {
                    stack.push curr
                    level = it.menuLevel
                } else {
                    while(it.menuLevel < level) {
                        stack.pop()
                        level--
                    }
                }
                stack[-1].items << item
            }
            curr = item
            map[it.id] = item
        }


        // get menu items
        getMenuItems(root, roles).each {
            map[it.menuId].items << [
                    label: chinese ? it.labelCn : it.labelEn,
                    url: it.url.replace('${userId}', userId)
            ]
        }

        // clear unused menu
        clearMenu(menus)
        return menus
    }

    private getMenusByRoot(String root) {
        MenuDto.executeQuery '''
select new map(
  id as id,
  labelCn as labelCn,
  labelEn as labelEn,
  menuLevel as menuLevel
) from MenuDto
where root = :root
''', [root: root]
    }

    private getMenuItems(String root, List<String> roles) {
        MenuItem.executeQuery '''
select new map(
  mi.id as id,
  m.id as menuId,
  mi.labelCn as labelCn,
  mi.labelEn as labelEn,
  mi.url as url
) from MenuItem mi
join mi.menu m
join mi.permission p
where p.id in (
  select p.id from 
  RolePermission rp
  join rp.role r
  join rp.permission p
  where r.id in(:roles)
) 
and m.id like concat(:root, '.%')
and mi.enabled = true
order by m.id, mi.displayOrder
''', [root: root, roles: roles]
    }

    private clearMenu(List menus) {
        for(int i = menus.size() - 1; i >= 0; i--) {
            def menu = menus[i]
            if(menu.items != null) {
                clearMenu(menu.items)
                if(menu.items.size == 0) {
                    menus.remove(menu)
                }
            }
        }
    }
}
