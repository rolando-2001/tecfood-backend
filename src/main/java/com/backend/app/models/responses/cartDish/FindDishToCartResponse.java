package com.backend.app.models.responses.cartDish;

import com.backend.app.persistence.entities.CartDishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message", "cartItem"})
public record FindDishToCartResponse(
        String message
        , CartDishEntity cartItem
) {
}
