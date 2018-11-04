package net.brilliance.manager.system;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.GUUISequenceGenerator;
import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.system.SystemSequence;
import net.brilliance.service.api.system.SystemSequenceService;

@Component
@Transactional
public class SystemSequenceManager {
	@Inject
	private SystemSequenceService bizService;

	public String requestSerialNo(String objectName){
		Long nextGUUId = queryNextGUID(objectName);//GUUISequenceGenerator.getInstance().nextGUUId(objectType);
		persistSequenceMap(objectName);
		System.out.println("After persist sequence map @" + Calendar.getInstance().getTime());
		return 
				new StringBuilder(objectName.toUpperCase())
				.append(String.format("%12d", nextGUUId.longValue()))
				.toString();
	}

	private Long queryNextGUID(String objectName){
		SystemSequence systemSequence = bizService.getOne(objectName);
		return systemSequence.getValue();
	}

	@Async
	private void persistSequenceMap(String objectName){
		SystemSequence systemSequence = bizService.getOne(objectName);
		systemSequence.increase(1);
		bizService.saveOrUpdate(systemSequence);
		System.out.println("Inside persist sequence map @" + Calendar.getInstance().getTime());
	}

	public SystemSequence registerSequence(String sequentialNumber) {
		long value = CommonUtility.extractEmbeddedNumber(sequentialNumber);
		int firstIdx = sequentialNumber.indexOf(String.valueOf(value));
		String sequence = sequentialNumber.substring(0, firstIdx);
		return registerSequence(sequence, value);
	}

	public SystemSequence registerSequence(String sequence, long value) {
		SystemSequence regSystemSequence = bizService.getOne(sequence);
		if (null==regSystemSequence){
			regSystemSequence = SystemSequence.builder()
					.name(sequence)
					.value(Long.valueOf(value))
					.build();
		} else {
			regSystemSequence.setValue(value);
		}
		return bizService.saveOrUpdate(regSystemSequence);
	}

	public void initializeSystemSequence(){
		Map<String, Long> guuidMap = ListUtility.createMap();
		List<SystemSequence> systemSequences = bizService.getObjects();
		for (SystemSequence systemSequence :systemSequences){
			guuidMap.put(systemSequence.getName(), systemSequence.getValue());
		}

		GUUISequenceGenerator.getInstance().initiatedStartedNumber(guuidMap);
	}
}
