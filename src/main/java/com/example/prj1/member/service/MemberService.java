package com.example.prj1.member.service;

import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.entity.Member;
import com.example.prj1.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void add(MemberForm data) {
        // 새 엔티티객체 생성해서
        Member member = new Member();
        // data에 있는 것 entity 에 옮겨 담고
        member.setId(data.getId());
        member.setPassword(data.getPassword());
        member.setNickName(data.getNickName());
        member.setInfo(data.getInfo());

        // repository.save()
        memberRepository.save(member);
    }
}