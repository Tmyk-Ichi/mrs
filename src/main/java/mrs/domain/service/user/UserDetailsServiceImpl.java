package mrs.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mrs.domain.model.LoginUserDetails;
import mrs.domain.model.User;
import mrs.domain.repository.user.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	//DBからユーザー情報を検索するメソッドを実装したクラス
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//ユーザー名で検索
		User user = userMapper.findByUserId(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username + "is not found");
		}
		
		
		return new LoginUserDetails(user);
	}

}
