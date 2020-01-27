package study.huhao.demo.infrastructure.persistence.user;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

  void insert(@Param("user") UserPO user);

  Optional<UserPO> findById(@Param("id") String id);
}
