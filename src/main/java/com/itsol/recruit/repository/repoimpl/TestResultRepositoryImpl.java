package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.dto.TestResultDTO;
import com.itsol.recruit.repository.repoext.TestResultRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TestResultRepositoryImpl implements TestResultRepository {

    private  final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TestResultRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<TestResultDTO> getStatisticResultTest(String userId) {
        String query = "";
        Map<String, Object> parameters = new HashMap<>();

        query += "with temp as (select u.id, t.id as test_result_id, t.time_accepted,t.answer_id, u.email  from public.test_result t\n" +
                "left join users u on t.user_id = u.id " +
                "where t.user_id like :p_user_id)";
        parameters.put("p_user_id", userId);
        query += "select temp.test_result_id, a.id as answer_id, temp.email, temp.time_accepted, q.id as question_id, q.title,\n" +
                "a.answer, a.corect_answer from temp " +
                "left join answers a on temp.answer_id = a.id " +
                "left join questions q on a.question_id = q.id " +
                "where temp.time_accepted = (select max(temp.time_accepted) from temp)";
        return  namedParameterJdbcTemplate.query(query, parameters, new TestResultRepositoryImpl.ResultStatisticMaper());
    }

    class ResultStatisticMaper implements RowMapper<TestResultDTO> {
        @Override
        public TestResultDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestResultDTO testResultDTO = new TestResultDTO();
            testResultDTO.setTestResultId(rs.getString("test_result_id"));
            testResultDTO.setEmail(rs.getString("email"));
            testResultDTO.setAnswer(rs.getString("answer"));
            testResultDTO.setTitle(rs.getString("title"));
            testResultDTO.setCorectAnswer(rs.getInt("corect_answer"));
            testResultDTO.setQuestionId(rs.getString("question_id"));
            testResultDTO.setAnswerId(rs.getString("answer_id"));
            testResultDTO.setTimeAccepted(rs.getString("time_accepted"));
            return testResultDTO;
        }
    }
}