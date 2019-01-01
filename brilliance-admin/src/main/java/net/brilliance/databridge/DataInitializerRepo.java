/**
 * 
 */
package net.brilliance.databridge;

import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.common.Address;

/**
 * @author ducbq
 *
 */
public class DataInitializerRepo {
	public final static int NUMBER_OF_CATALOGUE_SUBTYPES_GENERATE = 500;
	public final static int NUMBER_TO_GENERATE = 5000;
	public final static String DEFAULT_COUNTRY = "Việt Nam";

	public static Address[] buildAddresses() {
		String[] cities = new String[] { "Sài Gòn", "Biên Hòa", "Đồng Xoài", "Tây Ninh", "Lái Thiêu", "Đà Lạt", "Bảo Lộc", "Phan Thiết", "Nha Trang", "Sông Cầu", "Quy Nhơn",
				"Quảng Ngãi", "Đà Nẵng", "Hội An", "Huế", "Hà Nội", "Móng Cái", "Cẩm Phả", "Thác Bản Giốc", "Tuy Hòa", "Cam Lộc", "Bến Tre", "Cần Thơ", "Bạc Liêu", "Mỹ Tho", "Sa Đéc" };
		List<Address> addresses = ListUtility.createArrayList();
		Random randomGenerator = new Random();
		Faker faker = new Faker();
		for (int i = 0; i < NUMBER_TO_GENERATE; ++i) {
			addresses.add(
					Address.builder()
					.country(DEFAULT_COUNTRY)
					.city(cities[randomGenerator.nextInt(cities.length)])
					.address(faker.address().fullAddress())
					.state(faker.address().cityName())
					.postalCode(faker.address().zipCode()).build());
		}
		return addresses.toArray(new Address[0]);
	}

	public static final Byte[] CATALOGUE_SUBTYPE_LEVELS = new Byte[] {10, 11, 12, 20, 21, 22, 30, 31, 32, 40, 41, 42};
}
