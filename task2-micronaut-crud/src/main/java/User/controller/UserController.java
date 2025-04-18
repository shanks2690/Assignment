package User.controller;

import User.entity.User;
import User.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.Optional;

@Controller("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Post
    public HttpResponse<User> create(@Body User user) {
        System.out.println("User received "+ user);
        return HttpResponse.created(userService.create(user));
    }

    @Get("/{id}")
    public HttpResponse<User> get(Long id) {
        Optional<User> user = userService.get(id);
        return user.map(HttpResponse::ok).orElse(HttpResponse.notFound());
    }

    @Put("/{id}")
    public HttpResponse<User> update(Long id, @Body User updatedUser) {
        return userService.update(id, updatedUser)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Delete("/{id}")
    public HttpResponse<?> delete(Long id) {
        return userService.delete(id) ? HttpResponse.ok() : HttpResponse.notFound();
    }
}

