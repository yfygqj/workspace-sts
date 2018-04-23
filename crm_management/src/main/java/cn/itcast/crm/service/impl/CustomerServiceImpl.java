package cn.itcast.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	// 注入DAO
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findNoAssociationCustomers() {
		// fixedAreaId is null
		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findHasAssociationFixedAreaCustomers(
			String fixedAreaId) {
		// fixedAreaId is ?
		return customerRepository.findByFixedAreaId(fixedAreaId);
	}

	@Override
	public void associationCustomersToFixedArea(String customerIdStr,
			String fixedAreaId) {
		// 解除关联动作
		customerRepository.clearFixedAreaId(fixedAreaId);

		// 切割字符串 1,2,3
		if (StringUtils.isBlank(customerIdStr)) {
			return;
		}
		String[] customerIdArray = customerIdStr.split(",");
		for (String idStr : customerIdArray) {
			Integer id = Integer.parseInt(idStr);
			customerRepository.updateFixedAreaId(fixedAreaId, id);
		}
	}

}
