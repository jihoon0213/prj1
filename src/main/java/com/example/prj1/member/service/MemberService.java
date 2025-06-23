package com.example.prj1.member.service;

import com.example.prj1.member.dto.MemberDto;
import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.dto.MemberListInfo;
import com.example.prj1.member.entity.Member;
import com.example.prj1.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void add(MemberForm data) {

        Optional<Member> db = memberRepository.findById(data.getId());

        if (db.isEmpty()) {
            Optional<Member> byNickName = memberRepository.findByNickName(data.getNickName());
            if (byNickName.isEmpty()) {
                // 새 엔티티객체 생성해서
                Member member = new Member();
                // data에 있는 것 entity 에 옮겨 담고
                member.setId(data.getId());
                member.setPassword(data.getPassword());
                member.setNickName(data.getNickName());
                member.setInfo(data.getInfo());

                // repository.save()
                memberRepository.save(member);
            } else {
                throw new DuplicateKeyException(data.getNickName() + "는 이미 있는 별명입니다.");
            }
        } else {
            throw new DuplicateKeyException(data.getId() + "는 이미 있는 아이디입니다.");
        }

    }

    public List<MemberListInfo> list() {
        return memberRepository.findAllBy();
    }

    public MemberDto get(String id) {
        Member member = memberRepository.findById(id).get();

        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setNickName(member.getNickName());
        dto.setInfo(member.getInfo());
        dto.setCreatedAt(member.getCreatedAt());
        return dto;

    }

    public boolean remove(MemberForm data) {
        Member member = memberRepository.findById(data.getId()).get();

        String dbPw = member.getPassword();
        String formPw = data.getPassword();

        if (dbPw.equals(formPw)) {
            memberRepository.delete(member);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(MemberForm data) {
        // 조회
        Member member = memberRepository.findById(data.getId()).get();

        String dbPw = member.getPassword();
        String formPw = data.getPassword();

        if (dbPw.equals(formPw)) {
            // 변경
            member.setNickName(data.getNickName());
            member.setInfo(data.getInfo());
            // 저장
            memberRepository.save(member);

            return true;
        } else {
            return false;
        }

    }

    public boolean updatePassword(String id, String oldPassword, String newPassword) {
        Member db = memberRepository.findById(id).get();

        String dbPw = db.getPassword();

        if (dbPw.equals(oldPassword)) {
            db.setPassword(newPassword);
            memberRepository.save(db);

            return true;
        } else {
            return false;
        }

    }
}