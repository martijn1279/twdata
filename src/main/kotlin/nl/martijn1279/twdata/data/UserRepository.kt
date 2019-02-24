package nl.martijn1279.twdata.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
@Transactional
class UserRepository {

    @Autowired
    private lateinit var entityManager: EntityManager

    fun findUser(ipAddress: String) =
            entityManager.createQuery("select user from Users user where user.ipadress = :ipAddress ORDER BY user.lastRequest DESC", Users::class.java)
                    .setParameter("ipAddress", ipAddress)
                    .setMaxResults(1)
                    .resultList
                    .firstOrNull()

    fun save(user: Users) =
            entityManager.persist(user)

    fun update(user: Users): Users =
            entityManager.merge(user)

    fun count(): Int =
            entityManager.createQuery("select user from Users user", Users::class.java)
                    .resultList.size
}