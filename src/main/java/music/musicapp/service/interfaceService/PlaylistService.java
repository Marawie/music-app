package music.musicapp.service.interfaceService;

import music.musicapp.dto.PlaylistDto;

public interface PlaylistService {

    PlaylistDto changePrivacyStatus(Long playlistId, boolean isPrivate);

}