package io.github.cunyu1943.demofesod.controller;

import io.github.cunyu1943.demofesod.dto.CreateUserRequest;
import io.github.cunyu1943.demofesod.dto.Result;
import io.github.cunyu1943.demofesod.dto.UserDTO;
import io.github.cunyu1943.demofesod.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @description: 用户控制器
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UserController
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 用户信息
     */
    @PostMapping
    public ResponseEntity<Result<UserDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("请求 POST /api/users 接口, username={}", request.getUsername());
        Result<UserDTO> result = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @GetMapping
    public ResponseEntity<Result<List<UserDTO>>> getAllUsers() {
        log.info("请求 GET /api/users 接口");
        Result<List<UserDTO>> result = userService.getAllUsers();
        return ResponseEntity.ok(result);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<UserDTO>> getUserById(@PathVariable Long id) {
        log.info("请求 GET /api/users/{} 接口", id);
        Result<UserDTO> result = userService.getUserById(id);
        if (result.getRespCode() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteUser(@PathVariable Long id) {
        log.info("请求 DELETE /api/users/{} 接口", id);
        Result<Void> result = userService.deleteUser(id);
        if (result.getRespCode() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 导出用户到Excel
     *
     * @param filePath 导出文件路径
     * @return 导出结果
     */
    @GetMapping("/export")
    public ResponseEntity<Result<String>> exportUsersToExcel(@RequestParam String filePath) {
        log.info("请求 GET /api/users/export 接口, filePath={}", filePath);
        Result<String> result = userService.exportUsersToExcel(filePath);
        if (result.getRespCode() == 200) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 从Excel导入用户
     *
     * @param file 上传的文件
     * @return 导入结果
     */
    @PostMapping("/import")
    public ResponseEntity<Result<Integer>> importUsersFromExcel(@RequestParam("file") MultipartFile file) {
        log.info("请求 POST /api/users/import 接口, fileName={}", file.getOriginalFilename());
        try {
            // 保存上传的文件
            String uploadDir = "uploads";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = uploadDir + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            Result<Integer> result = userService.importUsersFromExcel(filePath);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail("文件上传失败: " + e.getMessage()));
        }
    }
}
