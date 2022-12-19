package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * <p>
 *     Provide services to insert, update, retrieve and delete products
 *     from cart based on currently logged-in user id
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetail, Integer> {

    /**
     * <p>
     *     Deletes all cart details by user's id, of particular user when user needs to empty cart
     *     or if user placed order through cart
     * </p>
     * @param userId - To delete products from cart
     */
    @Modifying
    @Transactional
    @Query(value = "update cart_detail set is_active = 0 where cart_id = "
            + "(select id from cart where user_id = ?1 and is_active = 1 )",
            nativeQuery = true)
    void deleteCartDetailsByUserId(Integer userId);
}
