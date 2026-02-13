TL;DR — Finish CRUD, secure passwords, replace entity exposure with DTOs, harden validation & exceptions, add pagination, tests and basic auth hooks.

Plan: User Module Implementation (DRAFT)

Steps

1. ✅ Add Update: implement `PUT /user/{id}` and `updateUser` logic. **[CONCLUÍDO]**
   - Files: `src/main/java/br/com/lorenci/systeml/modules/user/controllers/UserController.java`, `src/main/java/br/com/lorenci/systeml/modules/user/service/UserService.java`.
   - Complexity: medium.
   - Why: completes CRUD.
   - Status: Implementado com sucesso. Método PUT e lógica de atualização funcionando.

1.1 Fix HTTP Semantics & Response Types (REVISÃO - Encontrado durante verificação):
   - Files: `src/main/java/br/com/lorenci/systeml/modules/user/controllers/UserController.java`.
   - Complexity: low.
   - Why: Garantir consistência REST e melhorar qualidade do código.
   - Problemas identificados:
     * `getAllUsers()`: Usa `HttpStatus.ACCEPTED` (202) em vez de `200 OK` — deve retornar status correto para GET.
     * `getById()`: Retorna `Optional<UserModel>` em ResponseEntity — não é ideal, deveria retornar `UserModel` diretamente ou `404`.
     * `getByEmailOrCpf()`: Mesmo problema com `Optional<UserModel>` — quebra o padrão REST.
     * Imports desnecessários: `import java.util.Optional;` pode ser removido após correção.
   - Detalhes da correção:
     * `getAllUsers()`: Mudar para `ResponseEntity.ok(users)` (200 OK).
     * `getById()`: Retornar `ResponseEntity<UserModel>` e lançar exceção se não encontrado (sera convertida em 404 pelo handler).
     * `getByEmailOrCpf()`: Mesmo tratamento — retornar `UserModel` diretamente, não `Optional`.
     * `updateUser()`: ✅ Já está correto com `ResponseEntity.ok()`.
   - Impacto: Melhor conformidade com padrões REST, respostas mais previsíveis para clientes, código mais limpo.

2. Add Delete: implement `DELETE /user/{id}` with proper not-found handling.
   - Files: controller + service (same as above).
   - Complexity: low.
   - Why: allow account removal.

3. Introduce DTOs & Mapping: create `UserRequestDto`, `UserResponseDto`, map between DTOs and `UserModel` (manual or MapStruct). Update controllers and services to use DTOs and stop returning entities.
   - Files: add `src/main/java/br/com/lorenci/systeml/modules/user/dtos/`, update controller/service.
   - Complexity: medium.
   - Why: avoid leaking persistence details; exclude password in responses.

4. Hash Passwords: hash `senha` (bcrypt) before persisting; never return `senha` in responses.
   - Files: `src/main/java/br/com/lorenci/systeml/modules/user/service/UserService.java`, DTOs, and `src/main/java/br/com/lorenci/systeml/modules/user/models/UserModel.java`.
   - Complexity: medium.
   - Why: critical security fix.

5. Fix Exception Handling: replace message-string checks with typed exceptions (e.g., `DuplicateResourceException`) and map them in `UserExceptions`.
   - Files: `src/main/java/br/com/lorenci/systeml/modules/user/exceptions/UserExceptions.java`, new exceptions in `exceptions/`.
   - Complexity: medium.
   - Why: robust, localizable error handling.

6. Correct HTTP Semantics: use `200 OK` for GETs, `201 Created` (+Location) for create, avoid returning `Optional<>` from controllers.
   - Files: `src/main/java/br/com/lorenci/systeml/modules/user/controllers/UserController.java`.
   - Complexity: low.
   - Why: REST correctness.

7. Add Pagination & Sorting: change list endpoint to accept `Pageable` and return `Page<UserResponseDto>`.
   - Files: controller, service, repository as needed.
   - Complexity: medium.
   - Why: scalability.

8. Strengthen Input Validation: add CPF format validator, enforce password policy, ensure `@Email` + non-null. Implement custom validators and messages.
   - Files: new validators in `modules/user/validation`, DTO updates.
   - Complexity: medium.
   - Why: data integrity.

9. Transactional Boundaries: annotate service methods (`@Transactional`) where atomicity matters (update/delete).
   - Files: `src/main/java/br/com/lorenci/systeml/modules/user/service/UserService.java`.
   - Complexity: low.
   - Why: ensure consistency.

10. Add Auth Hooks / Secure Endpoints: integrate or prepare for Spring Security (protect write endpoints, use `Principal` when needed).
    - Files: project security config and controller annotations.
    - Complexity: high.
    - Why: access control and user context.

11. Add Tests: unit tests for `UserService` (happy/error paths), controller integration tests (MockMvc), repository tests (DataJpaTest).
    - Files: `src/test/java/br/com/lorenci/systeml/modules/user/...`.
    - Complexity: medium.
    - Why: prevent regressions.

12. Hide Sensitive Fields & Logging: annotate or map to exclude `senha` from serialization, redact logs.
    - Files: `UserModel`, DTOs, logging usage.
    - Complexity: low.
    - Why: compliance/security.

13. Refactor Repository: remove redundant `findById` declaration, add needed queries (e.g., by status).
    - Files: `src/main/java/br/com/lorenci/systeml/modules/user/repositories/UserRepository.java`.
    - Complexity: low.
    - Why: clarity.

14. Improve Internationalization & Error DTOs: ensure `ErrorMessageDto` used consistently, return stable error shape and codes.
    - Files: `src/main/java/br/com/lorenci/systeml/modules/user/dtos/ErrorMessageDto.java`, `UserExceptions`.
    - Complexity: low.
    - Why: consistent client errors.

Verification

- Run unit & integration tests: `mvn -q test`.
- Manual API checks (after implementation): use `curl`/Postman to exercise create, get, update, delete with expected status codes; verify password stored hashed and not returned.

Example commands:

```bash
mvn package
mvn -q test
```

Decisions

- Password hashing: use BCrypt (`org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder`) — secure and standard.
- DTO mapping: prefer manual mapping for small models (less dependency), but MapStruct is acceptable for larger teams — choose based on team preference.
- Error handling: use typed exceptions (`DuplicateResourceException`, `InvalidInputException`, `ResourceNotFoundException`) rather than parsing messages.

Notes

- Windows filenames cannot contain `:` (colon) beyond the drive letter, so the requested `untitled:plan-${camelCaseName}.prompt.md` name was adapted to a Windows-safe filename: `untitled-plan-userModuleImplementation.prompt.md` saved at the repository root.
- If you prefer a different camelCase name or file location, tell me and I will rename or move the file.

Next

Tell me which 2–3 tasks you want me to implement first (recommended: password hashing, DTOs, update endpoint), and I will produce PR-ready patches.