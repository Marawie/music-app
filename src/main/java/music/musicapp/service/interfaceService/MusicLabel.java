package music.musicapp.service.interfaceService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MusicLabel {
    Resource uploadMusic(MultipartFile file, String genre, String getTextMusic) throws IOException;

    Resource downloadMusic(Long id) throws IOException;
}