package com.carcara.imagem_backend_security.util;

import com.carcara.imagem_backend_security.model.RegisterDTO;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.repository.projection.DadosUsuarioProjection;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DTOEncryptor {

    private static List<String> camposEncript = new ArrayList<>(Arrays.asList("cpf", "email", "foto", "nome", "telefone", "username","login"));


    public static <T> void encryptDTO(T registerDTO, SecretKey secretKey) throws Exception {
        Class<?> dtoClass = registerDTO.getClass();
        Field[] fields = dtoClass.getDeclaredFields();

        for (Field field : fields) {
            if(camposEncript.contains(field.getName())) {
                field.setAccessible(true);
                Object value = field.get(registerDTO);

                if (value != null && value instanceof String) {
                    String encryptedValue = EncryptionUtil.encrypt((String) value, secretKey);
                    field.set(registerDTO, encryptedValue);
                }
            }
        }
    }
    public static <T> void decryptDTO(T registerDTO, SecretKey secretKey) throws Exception {
        Class<?> dtoClass = registerDTO.getClass();
        Field[] fields = dtoClass.getDeclaredFields();
        for (Field field : fields) {
            if(camposEncript.contains(field.getName())){
                field.setAccessible(true);
                Object value = field.get(registerDTO);

                if (value != null && value instanceof String) {
                    String decryptedValue = EncryptionUtil.decrypt((String) value, secretKey);
                    field.set(registerDTO, decryptedValue);
                }
            }
        }
    }
}
