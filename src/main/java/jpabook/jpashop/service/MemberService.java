package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //기본
@RequiredArgsConstructor //final이 있는 필드만 생성자 만들어줌
public class MemberService {

    //@Autowired
    private final MemberRepository memberRepository; //테스트할 때 변경이 불가능함


    /*Setter Injection => 테스트 코드 작성시 mock을 직접 주입해줄 수 있음
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }*/

    /*생성자 Injection
    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    //회원 가입
    @Transactional //따로 설정하면 이게 우선권
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName()); //이름이 같은 회원이 동시에 insert 될 가능성을 고려해서 member의 name을 unique 제약조건으로 잡는걸 권장
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    //@Transactional(readOnly = true) //readOnly = true : jpa가 조회하는 곳에서 성능 최적화가 가능해짐
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

   // @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
