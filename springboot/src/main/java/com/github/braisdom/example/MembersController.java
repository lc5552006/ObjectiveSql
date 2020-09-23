package com.github.braisdom.example;

import com.github.braisdom.example.model.Member;
import com.github.braisdom.example.model.Order;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class MembersController {

    /**
     * The post body is in "resources/json/create_member.json"
     */
    @PostMapping("/members")
    public ResponseObject create(@RequestBody RequestObject rawMember) throws SQLException {
        Member dirtyMember = Member.newInstanceFrom(rawMember, false);
        Member member = Member.create(dirtyMember, true);
        return ResponseObject.createSuccessResponse(member);
    }

    @GetMapping("/members/{no}")
    public ResponseObject getMember(@PathVariable("no") String memberNo) throws SQLException {
        Member member = Member.queryByNo(memberNo);
        return ResponseObject.createSuccessResponse(member);
    }

    @GetMapping("/members")
    public ResponseObject getMembers() throws SQLException {
        List<Member> members = Member.queryAll();
        return ResponseObject.createSuccessResponse(members);
    }

    @GetMapping("/members/{no}/orders")
    public ResponseObject getMemberOrders(@PathVariable("no") String no) throws SQLException {
        Member member = Member.queryByNo(no, Member.HAS_MANY_ORDERS, Order.HAS_MANY_ORDER_LINES);
        return ResponseObject.createSuccessResponse(member);
    }

    @PutMapping("/members/{no}")
    public ResponseObject updateMember(@PathVariable("no") String memberNo,
                                       @RequestBody RequestObject rawMember) throws SQLException {
        Member member = Member.queryByNo(memberNo);
        Member.update(member.getId(), Member.newInstanceFrom(rawMember), true);
        return ResponseObject.createSuccessResponse();
    }

    @DeleteMapping("/members/{no}")
    public ResponseObject deleteMember(@PathVariable("no") String memberNo) throws SQLException {
        int deleteCount = Member.destroy("member_no = ?");
        return ResponseObject.createSuccessResponse(deleteCount);
    }
}
