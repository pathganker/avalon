package org.saber.avalon;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.saber.avalon.modules.system.dao.UserDao;
import org.saber.avalon.modules.system.pojo.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvalonApplicationTests {
	
	@Autowired 
	private UserDao userDao;

	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void daoTest() {
		List<UserDO> dos = this.userDao.getAll();
		System.out.println(dos);
	}

}

