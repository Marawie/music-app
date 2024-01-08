package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.MusicDto;
import music.musicapp.dto.MusicRequest;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Music;
import music.musicapp.repository.MusicRepository;
import music.musicapp.service.interfaceService.MusicService;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;


    @Override
    public MusicDto createNewMusic(MusicRequest musicRequest) throws IOException {
        Music music;
        final ModelMapper modelMapper = new ModelMapper();
        if (fileValidator(musicRequest.getMusicFile())) {
            final byte[] bytes = Files.readAllBytes(musicRequest.getMusicFile().toPath());

            music = Music.builder()
                    .id(null)
                    .audio(bytes)
                    .textOfMusic(musicRequest.getTextOfMusic())
                    .title(musicRequest.getName())
                    .genre(musicRequest.getGenre())
                    .musicAddedFromArtist(musicRequest.getMusicAddedFromArtist())
                    .build();
            musicRepository.save(music);
        } else throw new RestException(ExceptionEnum.FILE_REQUEST_DENIED);

        return modelMapper.map(music, MusicDto.class);
    }


    private boolean fileValidator(File file) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(file);
        Parser parser = new AutoDetectParser();
        ParseContext context = new ParseContext();

        parser.parse(inputStream, handler, metadata, context);
        inputStream.close();

        String format = metadata.get("Content-Type");
        long fileSize = file.length();
        if ("audio/mpeg".equals(format) && fileSize > 0) {
            return true;
        } else return false;
    }
}

