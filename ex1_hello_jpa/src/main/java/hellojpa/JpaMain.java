package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        //트랜잭션 시작
        tx.begin();

        try {
            /**
             * 멤버 저장
             */
//            Member member = new Member();
//
//            member.setId(1L);
//            member.setName("helloA");
//
//            em.persist(member);
//            tx.commit();
            /**
             * 멤버 조회
             */
//            Member findMember = em.find(Member.class, 1L);

            /**
             * 멤버 수정
             */
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
//            tx.commit();
            /**
             * 멤버 삭제
             */
//            Member findMember = em.find(Member.class, 1L);
//            em.remove(findMember);
//            tx.commit();
            /**
             * JPQL을 이용한 조회
             */
//            List<Member> members = em.createQuery("select m from Member m", Member.class)
//                    .setFirstResult(3)
//                    .setMaxResults(5)
//                    .getResultList();
//            for (Member member : members) {
//                System.out.println("member = " + member.getName());
//            }

            /**
             * 영속성 컨텍스트
             */
//            // 비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("helloA");
//
//            //영속
//            em.persist(member);
//
//            //준영속
//            em.detach(member);
//
//            //삭제
//            em.remove(member);

            /**
             * 영속성 컨텍스트 1차 캐시 조회
             */
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("helloA");
//
//            //영속, 1차 캐시에만 저장
//            em.persist(member);
//
//            // 여기서는 db에서 조회하는 것이 아니라 1차 캐시에서 조회함
//            Member findMember = em.find(Member.class, 100L);
//            System.out.println("findMember.getName() = " + findMember.getName());
//
//            tx.commit();

            /**
             * 영속성 컨텍스트 동일성 보장
             */
//            Member findMember1 = em.find(Member.class, 100L);
//            Member findMember2 = em.find(Member.class, 100L);
//            System.out.println("result = " + (findMember1 == findMember2)); //동일성 비교 true

            /**
             * 쓰기 지연
             */
//            Member member1 = new Member(101L, "A");
//            Member member2 = new Member(102L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println("==============");
//
//            tx.commit();

            /**
             * 변경 감지
             */
//            Member member = em.find(Member.class, 101L);
//            member.setName("C");
//            System.out.println("==============");
//            tx.commit();

            /**
             * 플러시
             */
//            Member member = new Member(3L, "A");
//            em.persist(member);
//            em.flush();
//            System.out.println("==============");
//            tx.commit();

            /**
             * 준영속
             */
            Member member = em.find(Member.class, 101L);
            member.setName("C");

            // 엔터티 객체 하나만
            em.detach(member);
            // 영속성 컨텍스트 전체 초기화
            em.clear();
            // 영속성 컨텍스트 종료
            em.close();

            System.out.println("==============");
            tx.commit();


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
