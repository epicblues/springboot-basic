package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.programmers.devcourse.voucher.engine.exception.VoucherDiscountDegreeOutOfRangeException;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class FileVoucherRepository implements
    VoucherRepository {

  private static final String DELIMITER_REGEX = "\\|\\|";
  private static final String DELIMITER = "||";
  public static final Function<Voucher, String> serializer = (Voucher voucher) -> {
    String template = "{1}{0}{2}{0}{3}";
    return MessageFormat.format(template, DELIMITER,
        voucher.getVoucherId(),
        VoucherType.mapToTypeId(voucher), // 바우처의 타입을 알면 id를 받아올 수 있다.
        voucher.getDiscountDegree());
  };
  // 바우처를 로드했을 때 먼저 파일 스트림을 연다.
  private final Map<UUID, Voucher> memoryStorage = new LinkedHashMap<>();
  private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
  private final FileChannel fileChannel;

  public FileVoucherRepository(
      FileChannel fileChannel) {
    this.fileChannel = fileChannel;

    Arrays.stream(fileChannel.readAllLines()).forEach(line -> {
      var fields = line.split(DELIMITER_REGEX);

      var voucherId = UUID.fromString(fields[0]);
      var voucherMapper = VoucherType.from(fields[1]);
      if (voucherMapper.isEmpty()) {
        return;
      }

      var discountDegree = Long.parseLong(fields[2].replace(",", ""));
      
      try {
        memoryStorage.put(voucherId,
            voucherMapper.get().getFactory().create(voucherId, discountDegree));
      } catch (VoucherDiscountDegreeOutOfRangeException e) {
        logger.error(MessageFormat.format("{0} : Not valid voucher", voucherId));
      }

    });
  }

  @Override
  public UUID save(Voucher voucher) throws VoucherException {

    try {
      fileChannel.save(voucher, serializer);
    } catch (IOException e) {
      throw new VoucherException(
          MessageFormat.format("Saving Voucher({0}) failed because of IOException",
              voucher.getVoucherId()));
    }
    memoryStorage.put(voucher.getVoucherId(), voucher);
    return voucher.getVoucherId();
  }

  @Override
  public Optional<Voucher> getVoucherById(UUID voucherId) {
    return Optional.ofNullable(memoryStorage.get(voucherId));
  }

  @Override
  public List<Voucher> getAllVouchers() {
    return List.copyOf(memoryStorage.values());
  }

  @Override
  public List<Voucher> getUnassignedVouchers() {
    return null;
  }

  @Override
  public Voucher update(Voucher voucher) {
    return null;
  }


}
