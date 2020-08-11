package mrs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


//セキュリティ設定用クラス
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserDetailsService userDetailsService;


	//フォームの値と比較するDBから取得したパスワードは
	//暗号化されているのでフォームの値も暗号化するために利用
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		//静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers("/webjars/∗∗", "/css/∗∗","/images/**","/javascript/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//ログイン不要ページの設定
		http.authorizeRequests()
		.antMatchers("/webjars/**").permitAll() //webjarsへアクセス許可
		.antMatchers("/css/**").permitAll() //cssへアクセス許可
		.antMatchers("/login").permitAll() //ログインページは直リンクOK
		.antMatchers("**").permitAll()
		.anyRequest().authenticated(); //それ以外は直リンク禁止

		//ログイン処理
		http
		.formLogin()
		.loginProcessingUrl("/login")//ログイン処理のパス
		.loginPage("/login")//ログインページの指定
		.failureUrl("/login")//ログイン失敗時の遷移先
		.usernameParameter("userId")//ログインページのユーザーID
		.passwordParameter("password")//ログインページのパスワード
		.defaultSuccessUrl("/rooms", true);


		//ログアウト処理
		http
		.logout()
		//springのデフォルトではログアウト処理はPOSTメソッドで送るがGETメソッドの場合の処理
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
		//POSTメソッドのログアウトのURL
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login"); //ログアウト成功後のURL

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}


	//認証処理
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}





}
