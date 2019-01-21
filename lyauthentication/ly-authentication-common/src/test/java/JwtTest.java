import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {
 
    private static final String pubKeyPath = "H:\\rsa.pub";
 
    private static final String priKeyPath = "H:\\rsa.pri";
 
    private PublicKey publicKey;
 
    private PrivateKey privateKey;
 
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }
 
    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }
    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(2L, "tom"), privateKey, 5);
        System.out.println("token = " + token);
    }
 
    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MiwidXNlcm5hbWUiOiJ0b20iLCJleHAiOjE1NDc3NDQxNTN9.Fb6TSW6BhhqpF7LRKarbrqzUwLfTIclqFuf157fRjCHwic99XJdKCk48CG-FwZKj6pTOecuTDGi9j1KIdWT4KHdeu_67Jsfg3uxGs2RNAImCMDlY0hlHQKvpnBbksO5RzvpG88t6gPiJQYDHQ8uPmfirUoB7nrnt01fJFipTRpA";
 
        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
