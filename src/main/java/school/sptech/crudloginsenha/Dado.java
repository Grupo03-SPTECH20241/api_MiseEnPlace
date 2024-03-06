package school.sptech.crudloginsenha;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Dado {
    private String login;
    private String senha;
     @JsonIgnore
    private byte[] senhaCifrada;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws Exception {
        SecretKey chaveSimetrica = geraChave();
        IvParameterSpec ivParameterSpec = geraIv();
        this.senhaCifrada = encripta(senha, chaveSimetrica, ivParameterSpec);
        this.senha = Arrays.toString(this.senhaCifrada);
    }


    public byte[] getSenhaCifrada() {
        return senhaCifrada;
    }

    public void setSenhaCifrada(byte[] senhaCifrada) {
        this.senhaCifrada = senhaCifrada;
    }

    public static SecretKey geraChave() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }
    public static IvParameterSpec geraIv() {
        byte[] vetorInicializacao = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(vetorInicializacao);
        return new IvParameterSpec(vetorInicializacao);
    }
    public static byte[] encripta(String entrada, SecretKey chave, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, chave, iv);
        return cipher.doFinal(entrada.getBytes(StandardCharsets.UTF_8));
    }

    public static String decripta(byte[] textoCifrado, SecretKey chave, IvParameterSpec iv) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, chave, iv);
        byte[] textoAberto = cipher.doFinal(textoCifrado);

        return new String(textoAberto);
    }
}
