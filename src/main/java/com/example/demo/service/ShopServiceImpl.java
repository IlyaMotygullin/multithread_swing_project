package com.example.demo.service;

import com.example.demo.repository.ShopRepository;
import com.example.demo.window.window_user.window_entrance.EntranceWindow;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "shopService")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopServiceImpl implements ShopService {
    final ShopRepository shopRepository;

    public ShopServiceImpl(@Qualifier(value = "shopRepository") ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public void menu() {
        EntranceWindow entranceWindow = new EntranceWindow(shopRepository);
        entranceWindow.createWindow();
    }
}
