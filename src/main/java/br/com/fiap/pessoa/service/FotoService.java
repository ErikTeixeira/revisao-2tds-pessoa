package br.com.fiap.pessoa.service;

import br.com.fiap.pessoa.entity.Foto;
import br.com.fiap.pessoa.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final String IMAGEM_FOLDER = System.getProperty("user.dir") + "/drive/fotos";

    private final FotoRepository repo;

    public Foto save(Foto foto, MultipartFile imagem) {
        Foto saved = null;
        if (upload(imagem, foto)) saved = repo.save(foto);
        return saved;
    }

    public boolean upload(MultipartFile file, Foto foto) {


        if (file.isEmpty()) {
            throw new RuntimeException("Empty file");
        }

        Path destination = Paths
                .get(IMAGEM_FOLDER)
                .resolve(foto.getSrc())
                .normalize()
                .toAbsolutePath();

        try {

            if (!Files.exists(Path.of(IMAGEM_FOLDER))) Files.createDirectories(
                    Path.of(IMAGEM_FOLDER)
            );

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return true;

        } catch (IOException e) {
            System.err.println("[ IOEXCEPTION ][  FOTO - UPLOAD  ] -  ERRO NO UPLOAD DO ARQUIVO:  " + e.getMessage());
            return false;
        }
    }
}