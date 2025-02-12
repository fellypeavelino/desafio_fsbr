/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desafio.fsbr.desafio_fsbr.config;

import com.desafio.fsbr.desafio_fsbr.entities.Usuario;
import com.desafio.fsbr.desafio_fsbr.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Usuario
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        //ALTER TABLE documento_pdf
        //MODIFY COLUMN documento_pdf LONGBLOB;
        if (usuarioRepository.count() == 0) {
            Usuario defaultUsuario = new Usuario();
            defaultUsuario.setLoguin("admin");
            defaultUsuario.setSenha("admin123");
            usuarioRepository.save(defaultUsuario);
            logger.info("Usuário padrão inserido no banco de dados.");
        } else {
            logger.warn("Usuários já existentes no banco de dados.");
        }
    }
    
}
