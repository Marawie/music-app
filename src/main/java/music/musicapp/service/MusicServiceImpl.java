package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Genre;
import music.musicapp.model.Music;
import music.musicapp.repository.MusicRepository;
import music.musicapp.service.interfaceService.MusicService;
import org.apache.tika.Tika;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Resource downloadMusic(Long id) {
        byte[] audio = musicRepository.findById(id).orElseThrow(
                () -> new RestException(ExceptionEnum.FILE_READ_ERROR)).getAudio();
        return new ByteArrayResource(audio);
    }

    @Override
    public Resource uploadMusic(MultipartFile file, String genre, String getTextMusic) throws IOException {

        if (!file.getResource().exists()) {
            throw new RestException(ExceptionEnum.FILE_REQUEST_DENIED);
        }

        final Tika tika = new Tika();
        final String mimeType = tika.detect(file.getInputStream());

        validateFile(file, mimeType);

        final Music music = Music.builder()
                .id(null)
                .genre(new Genre(genre))
                .title(file.getName())
                .audio(file.getBytes())
                .textOfMusic(getTextMusic)
                .build();
        musicRepository.save(music);

        return new ByteArrayResource(music.getAudio());
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
        if (!ALLOWED_MIME_TYPES.contains(mimeType)) {
            throw new RestException(ExceptionEnum.WRONG_MIME_TYPES);
        } else
            return true;
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