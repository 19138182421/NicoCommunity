package com.example.nicocommunity.Service.Category;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public interface CategoryService {

    List<Map<String,Object>> getCatItems(Integer catPid);

    List<Map<String, Object>> getCategory(Integer catPid);
}
