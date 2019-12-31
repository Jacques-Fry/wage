package com.transeesoft.wage.dao;

import com.transeesoft.wage.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

  User findByPasswordAndTel( String password, String tel);

  User findByPasswordAndUsername( String password,String username);

  Integer countByTel(String tel);

  Integer countById(Integer id);

  Integer countByIdAndStatus(Integer id,String status);

  Integer countByUsername(String username);

  Integer countByPasswordAndId(String password, Integer id);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "UPDATE user SET tel=?1 WHERE id=?2")
  void updateTel(String tel, Integer id);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "UPDATE user SET username=?1 WHERE id=?2")
  void updateUserName(String username, Integer id);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "UPDATE user SET password=?1 WHERE id=?2")
  void updatePassword(String password, Integer id);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "DELETE FROM user WHERE id=?1")
  void delById(Integer id);

  Page<User> findByUsernameLikeAndTelLikeAndRoleIdIn(String username, String tel, Integer[] roleId, Pageable pageable);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "UPDATE user SET role_id=?1 WHERE id=?2")
  void updateRoleId(Integer roleId,Integer id);
}
