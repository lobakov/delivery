package com.github.lobakov.delivery.core.application.command.create.order

import java.util.*

class CreateOrderCommand(val basketId: UUID, val address: String, val weight: Int)
