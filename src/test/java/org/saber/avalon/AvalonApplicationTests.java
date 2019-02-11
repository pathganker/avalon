package org.saber.avalon;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.saber.avalon.modules.system.dao.IUserDao;
import org.saber.avalon.modules.system.pojo.dos.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvalonApplicationTests {
	
	@Autowired 
	private IUserDao userDao;

	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void daoTest() {
		List<UserDO> dos = this.userDao.getAll();
		UserDO user = this.userDao.queryUserByName("admin");
		System.out.println(user);
		System.out.println(dos);
	}

}

