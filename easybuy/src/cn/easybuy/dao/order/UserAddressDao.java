package cn.easybuy.dao.order;

import java.util.List;
 
import cn.easybuy.entity.UserAddress;
import cn.easybuy.param.UserAddressParam;

/**
 * Created by bdqn on 2016/5/12.
 * addObject(UserAddress userAddress)
 * getRowList(params)
 */
public interface UserAddressDao   {
	
	public List<UserAddress> queryUserAddressList(UserAddressParam param);
	
	public Integer saveUserAddress(UserAddress userAddress);
	
	public UserAddress getUserAddressById(Integer addressId);

}
