package uz.nt.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.PaymentHistoryDto;
import uz.nt.orderservice.entity.PaymentHistory;
import uz.nt.orderservice.repository.PaymentHistoryRepository;
import uz.nt.orderservice.service.PaymentHistoryService;
import uz.nt.orderservice.service.mapper.PaymentHistoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentHistoryServiceImpl implements PaymentHistoryService {
    private final PaymentHistoryRepository historyRepository;
    private final PaymentHistoryMapper historyMapper;

    @Override
    public ResponseDto addHistory(PaymentHistoryDto paymentHistoryDto) {
        try {
            PaymentHistory paymentHistory = historyMapper.toEntity(paymentHistoryDto);

            historyRepository.save(paymentHistory);

            return ResponseDto.builder()
                    .code(0)
                    .message("Successfully saved")
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(-5)
                    .message("Payment history not saved: " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<PaymentHistoryDto> getById(Integer id) {
        try {
            Optional<PaymentHistory> paymentHistory = historyRepository.findById(id);

            if (paymentHistory.isEmpty()){
                return ResponseDto.<PaymentHistoryDto>builder()
                        .code(-4)
                        .message("Payment history not found")
                        .success(false)
                        .build();
            }

            PaymentHistoryDto paymentHistoryDto = historyMapper.toDto(paymentHistory.get());

            return ResponseDto.<PaymentHistoryDto>builder()
                    .code(0)
                    .message("")
                    .responseData(paymentHistoryDto)
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<PaymentHistoryDto>builder()
                    .code(-1)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<PaymentHistoryDto>> getAllHistories() {
        try {
            List<PaymentHistory> historyList = historyRepository.findAll();

            if (historyList.isEmpty()){
                return ResponseDto.<List<PaymentHistoryDto>>builder()
                        .code(-4)
                        .message("No data")
                        .success(true)
                        .build();
            }

            List<PaymentHistoryDto> paymentHistoryDtos = historyList.stream()
                    .map((historyMapper::toDto)).toList();

            return ResponseDto.<List<PaymentHistoryDto>>builder()
                    .code(0)
                    .message("Successfully sent")
                    .responseData(paymentHistoryDtos)
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<List<PaymentHistoryDto>>builder()
                    .code(-5)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        try {
            Optional<PaymentHistory> paymentHistory = historyRepository.findById(id);

            if (paymentHistory.isEmpty()){
                return ResponseDto.builder()
                        .code(-4)
                        .message("Not found")
                        .success(false)
                        .build();
            }

            historyRepository.delete(paymentHistory.get());

            return ResponseDto.builder()
                    .code(0)
                    .message("Successfully deleted")
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(-5)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }
}
