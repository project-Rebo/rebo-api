package hello.reboapi.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterRequest {
    private String name;
    private String email;
    private String password;
}