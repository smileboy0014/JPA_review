package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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
            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("helloA");

            //영속
            em.persist(member);

            //준영속
            em.detach(member);

            //삭제
            em.remove(member);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
