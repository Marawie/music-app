package music.musicapp.model.user;

import lombok.Getter;

@Getter
public enum FriendshipRequestState {
    ACCEPTED,
    WAITING_FOR_RESPONSE,
    REJECTED_BY_YOU,
    REJECTED_BY_USER,
    ACCEPTATION_REQUIRED,
    WITHDRAWN
}