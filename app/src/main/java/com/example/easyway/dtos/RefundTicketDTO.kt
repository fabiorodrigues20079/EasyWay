package com.example.easyway.dtos

data class RefundTicketDTO(val ticketId: String, val price: Double ,val userToRefund: String, val userToBuy: String)