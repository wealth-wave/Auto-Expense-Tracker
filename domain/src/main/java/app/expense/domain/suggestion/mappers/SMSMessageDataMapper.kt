package app.expense.domain.suggestion.mappers

import app.expense.domain.suggestion.models.SMSMessage
import app.expense.dtos.SMSMessageDTO

class SMSMessageDataMapper {
    fun mapToSMSMessage(smsMessageDTO: SMSMessageDTO) = SMSMessage(
        address = smsMessageDTO.address,
        body = smsMessageDTO.body,
        time = smsMessageDTO.time,
    )
}
