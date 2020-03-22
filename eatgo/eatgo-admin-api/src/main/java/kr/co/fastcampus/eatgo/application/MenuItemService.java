package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    //본래는 CRUD로 각 함수를 만들어 사용했지만 bulkUpdate를 통해 한 번에 조건을 통해 다뤘다.
    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        for(MenuItem menuItem: menuItems){
            update(restaurantId, menuItem);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if(menuItem.isDeleted()){
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurantId);
        menuItemRepository.save(menuItem);
    }
}
