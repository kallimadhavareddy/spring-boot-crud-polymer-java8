package be.ing.fundtransfer.repository;

import be.ing.fundtransfer.data.User;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

	@Query(value="SELECT * FROM users WHERE user_name=?0 ALLOW FILTERING")
	public User findByUsername(String userName);
	
	@Query(value="SELECT * FROM users WHERE email_id=?0 ALLOW FILTERING")
	public User findByEmail(String emailId);

	@Query(value="SELECT * FROM users WHERE email_id=?0 and mobile_number=?1 ALLOW FILTERING")
	public User findByEmailAndMobile(String emailId,String mobile);

	@Query(value="SELECT * FROM users ALLOW FILTERING")
	public List<User> findAllUsers();
}
/*
	CREATE KEYSPACE IF NOT EXISTS FundTransfer WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;


	CREATE TABLE IF NOT EXISTS FundTransfer.users_1 (
		id timeuuid,
		user_name text,
		password text,
		first_name text,
		last_name text,
		middle_name text,
		email_id text,
		address text,
		PRIMARY KEY (id)
		) ORDER BY (id DESC);

@PrimaryKey
private int id;

@Column(value = "user_name")
;

@Column(value = "password")
@Column(value = "first_name")
@Column(value = "last_name")
@Column(value = "middle_name")
@Column(value = "mobile_number")
@Column(value = "email_id")
@Column(value = "address")

*/
