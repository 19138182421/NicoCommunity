package com.example.nicocommunity.Service.Address;

import com.example.nicocommunity.Mapper.AddressMapper;
import com.example.nicocommunity.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Map<String, Object>> getAllAddress(String userId) {
        return addressMapper.getAllAddress(userId);
    }

    @Override
    public int addAddress(Address address) {
        return addressMapper.addAddress(address);
    }

    @Override
    public int deleteAddress(String addressId, Long userId) {
        return addressMapper.deleteAddress(addressId,userId);
    }

    @Override
    public int modify(Long userId) {
        return addressMapper.modify(userId);
    }

    @Override
    public int modifyAddress(Address address) {
        return addressMapper.modifyAddress(address);
    }
}
