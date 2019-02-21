package org.saber.avalon.common.shiro;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.saber.avalon.modules.system.pojo.dtos.UserDTO;
import org.springframework.stereotype.Service;



/**
 * 密码生成服务
 * <br>
 * 与CredentialsMatcher保持一致
 * @author <a mailto="lizheng8318@foxmail.com">leon</a>
 *
 */
@Service
public class UserPasswordHelper implements PasswordService {

    private String algorithmName = "md5";  
    private final int hashIterations = 2; 
		    
	/**
	 * 
	 */
	public UserPasswordHelper() {
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.authc.credential.PasswordService#encryptPassword(java.lang.Object)
	 */
	@Override
	public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
		UserDTO user= (UserDTO) plaintextPassword;
		String password = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations).toHex();
		return password;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.authc.credential.PasswordService#passwordsMatch(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
		UserDTO user = (UserDTO) submittedPlaintext;
		String submitPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations).toHex();
		return submitPassword.equals(encrypted);
	}

}
