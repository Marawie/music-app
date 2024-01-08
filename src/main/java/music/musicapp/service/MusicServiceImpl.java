package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Genre;
import music.musicapp.model.Music;
import music.musicapp.repository.MusicRepository;
import music.musicapp.service.interfaceService.MusicService;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("mp3", "wav", "flac");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("audio/mpeg", "audio/wav", "audio/flac");
    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
    private final MusicRepository musicRepository;


    @Override
    public String uploadFile(String path, MultipartFile file, String musicType, String musicText) throws IOException {
        Tika tika = new Tika();

        String mimeType = tika.detect(file.getBytes());
        validateFile(file, mimeType);
        final String fileName = file.getOriginalFilename();

        final String filePath = path + File.separator + fileName;

        File musicFile = new File(path);

        if (!musicFile.exists()) {
            musicFile.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        musicRepository.save(Music.builder()
                .id(null)
                .audio(file.getBytes())
                .title(fileName)
                .musicAddedFromArtist(LocalDate.now())
                .textOfMusic(musicText)
                .genre(new Genre(musicType))
                .build());

        return fileName;
    }


    @Override
    public InputStream getResourceFile(String path, String name) throws FileNotFoundException {
        String filePath = path + File.separator + name;
        return new FileInputStream(filePath);
    }

    private void validateFile(MultipartFile file, String mimeType) {
        validateExtension(file);
        isMusicMimeType(mimeType);
        validateFileSize(file);
    }

    private void validateExtension(MultipartFile file) {
        String fileExtension = getFileExtension(requireNonNull(file.getOriginalFilename()));
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new RestException(ExceptionEnum.WRONG_EXTENSION_FILE);
        }
    }

    private boolean isMusicMimeType(String mimeType) {
        return ALLOWED_MIME_TYPES.contains(mimeType);
    }


    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RestException(ExceptionEnum.FILE_TOO_BIG);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }
}

