package com.example.equipmentmanagement;

import com.example.equipmentmanagement.model.*;
import com.example.equipmentmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EquipmentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner preloadSubcategories(SubcategoryRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				Subcategory subcategory1 = new Subcategory();
				subcategory1.setName("MÁQUINAS ELÉCTRICAS");
				repo.save(subcategory1);

				Subcategory subcategory2 = new Subcategory();
				subcategory2.setName("HERRAMIENTA DE MANO");
				repo.save(subcategory2);

				Subcategory subcategory3 = new Subcategory();
				subcategory3.setName("MEDIA TENSIÓN");
				repo.save(subcategory3);

				Subcategory subcategory4 = new Subcategory();
				subcategory4.setName("EQUIPOS");
				repo.save(subcategory4);
			}
		};
	}

	@Bean
	public CommandLineRunner preloadEquipment(EquipmentRepository equipmentRepo, SubcategoryRepository subcategoryRepo) {
		return args -> {
			if (equipmentRepo.count() == 0) {
				// Cargar las subcategorías desde la base de datos
				Subcategory subcategory1 = subcategoryRepo.findByName("MÁQUINAS ELÉCTRICAS");
				Subcategory subcategory2 = subcategoryRepo.findByName("HERRAMIENTA DE MANO");
				Subcategory subcategory3 = subcategoryRepo.findByName("MEDIA TENSIÓN");
				Subcategory subcategory4 = subcategoryRepo.findByName("EQUIPOS");

				// Equipo 1
				Equipment equipment1 = new Equipment();
				equipment1.setName("MÁQUINA DE TALADRAR HILTI TE 2 S");
				equipment1.setCode("1218571");
				equipment1.setSerialNumber("");
				equipment1.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment1.setSubcategory(subcategory1);
				equipmentRepo.save(equipment1);

				// Equipo 2
				Equipment equipment2 = new Equipment();
				equipment2.setName("MÁQUINA DE TALADRAR HILTI TE 2-A22");
				equipment2.setCode("683 1203245");
				equipment2.setSerialNumber("253004");
				equipment2.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment2.setSubcategory(subcategory1);
				equipmentRepo.save(equipment2);

				// Equipo 3
				Equipment equipment3 = new Equipment();
				equipment3.setName("MÁQUINA DE TALADRAR HILTI SF 10W-A22");
				equipment3.setCode("683 1203097");
				equipment3.setSerialNumber("190030279");
				equipment3.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment3.setSubcategory(subcategory1);
				equipmentRepo.save(equipment3);

				// Equipo 4
				Equipment equipment4 = new Equipment();
				equipment4.setName("MÁQUINA DE TALADRAR HILTI TE 4-A22");
				equipment4.setCode("1203758");
				equipment4.setSerialNumber("2568774");
				equipment4.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment4.setSubcategory(subcategory1);
				equipmentRepo.save(equipment4);

				// Equipo 5
				Equipment equipment5 = new Equipment();
				equipment5.setName("MÁQUINA DE TALADRAR HILTI TE 4-A22");
				equipment5.setCode("1203751");
				equipment5.setSerialNumber("568465");
				equipment5.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment5.setSubcategory(subcategory1);
				equipmentRepo.save(equipment5);

				// Equipo 6
				Equipment equipment6 = new Equipment();
				equipment6.setName("MÁQUINA DE TALADRAR HILTI SF 6H-A22");
				equipment6.setCode("1203773");
				equipment6.setSerialNumber("201690391");
				equipment6.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment6.setSubcategory(subcategory1);
				equipmentRepo.save(equipment6);

				// Equipo 7
				Equipment equipment7 = new Equipment();
				equipment7.setName("MÁQUINA DE TALADRAR HILTI SF 6H-A22");
				equipment7.setCode("1203766");
				equipment7.setSerialNumber("201690378");
				equipment7.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment7.setSubcategory(subcategory1);
				equipmentRepo.save(equipment7);

				// Equipo 8
				Equipment equipment8 = new Equipment();
				equipment8.setName("RADIAL HILTI AG.125-A22");
				equipment8.setCode("13001487");
				equipment8.setSerialNumber("591345");
				equipment8.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment8.setSubcategory(subcategory2);
				equipmentRepo.save(equipment8);

				// Equipo 9
				Equipment equipment9 = new Equipment();
				equipment9.setName("RADIAL HILTI AG.125-A22");
				equipment9.setCode("1301494");
				equipment9.setSerialNumber("591350");
				equipment9.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment9.setSubcategory(subcategory2);
				equipmentRepo.save(equipment9);

				// Equipo 10
				Equipment equipment10 = new Equipment();
				equipment10.setName("MÁQUINA DE TALADRAR HILTI TE 2 S");
				equipment10.setCode("1218575");
				equipment10.setSerialNumber("");
				equipment10.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment10.setSubcategory(subcategory1);
				equipmentRepo.save(equipment10);

				// Equipo 11
				Equipment equipment11 = new Equipment();
				equipment11.setName("MÁQUINA DE TALADRAR HILTI TE 2 S");
				equipment11.setCode("1218573");
				equipment11.setSerialNumber("");
				equipment11.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment11.setSubcategory(subcategory1);
				equipmentRepo.save(equipment11);

				// Equipo 12
				Equipment equipment12 = new Equipment();
				equipment12.setName("MÁQUINA TALADRAR HILTI TE 2");
				equipment12.setCode("1213235");
				equipment12.setSerialNumber("");
				equipment12.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment12.setSubcategory(subcategory1);
				equipmentRepo.save(equipment12);

				// Equipo 13
				Equipment equipment13 = new Equipment();
				equipment13.setName("MÁQUINA DE TALADRAR BOSCH GBH 2-26 DFR");
				equipment13.setCode("1201003");
				equipment13.setSerialNumber("");
				equipment13.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment13.setSubcategory(subcategory3);
				equipmentRepo.save(equipment13);

				// Equipo 14
				Equipment equipment14 = new Equipment();
				equipment14.setName("CALADORA BOSCH GST 150 BCE");
				equipment14.setCode("1301357");
				equipment14.setSerialNumber("");
				equipment14.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment14.setSubcategory(subcategory2);
				equipmentRepo.save(equipment14);

				// Equipo 15
				Equipment equipment15 = new Equipment();
				equipment15.setName("PISTOLA DX A 40 HILTI");
				equipment15.setCode("002");
				equipment15.setSerialNumber("");
				equipment15.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment15.setSubcategory(subcategory2);
				equipmentRepo.save(equipment15);

				// Equipo 16
				Equipment equipment16 = new Equipment();
				equipment16.setName("RADIAL GRANDE");
				equipment16.setCode("680/015");
				equipment16.setSerialNumber("");
				equipment16.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment16.setSubcategory(subcategory2);
				equipmentRepo.save(equipment16);

				// Equipo 17
				Equipment equipment17 = new Equipment();
				equipment17.setName("DECAPADOR MAKITA");
				equipment17.setCode("683 1301329");
				equipment17.setSerialNumber("180900173");
				equipment17.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment17.setSubcategory(subcategory2);
				equipmentRepo.save(equipment17);

				// Equipo 18
				Equipment equipment18 = new Equipment();
				equipment18.setName("ASPIRADORA");
				equipment18.setCode("683 3500321");
				equipment18.setSerialNumber("");
				equipment18.setCurrentStatus(EquipmentStatus.DISPONIBLE);
				equipment18.setSubcategory(subcategory2);
				equipmentRepo.save(equipment18);


				// Continúa con los demás equipos de forma similar
			}
		};
	}

	@Bean
	public CommandLineRunner loadInitialOperators(OperatorRepository operatorRepository) {
		return args -> {
			// Verifica si ya existen operarios para evitar duplicados
			if (operatorRepository.count() == 0) {
				String[] names = {"Juan Pérez", "Ana García", "Carlos López", "Laura Martínez", "Pedro Sánchez"};

				for (String fullName : names) {
					String[] parts = fullName.split(" ");
					String firstName = parts[0];
					String lastName = parts[1];

					Operator operator = new Operator();
					operator.setName(firstName + " " + lastName); // Podrías separarlos si lo deseas, pero por ahora es un solo campo

					// Guardamos el operario en la base de datos
					operatorRepository.save(operator);
				}
			}
		};
	}


	@Bean
	public CommandLineRunner preloadWarehouseData(WorkRepository workRepository) {
		return args -> {
			// Verificar si ya existe una obra con ese nombre
			if (workRepository.count() == 0) {
				Work work = new Work();
				work.setName("80600");
				work.setLocation("Ubicación desconocida"); // Puedes ajustar la ubicación si lo necesitas

				// Guardamos la obra en la base de datos
				workRepository.save(work);
			}
		};
	}

	@Bean
	public CommandLineRunner preloadWarehouse(WarehouseRepository warehouseRepository) {
		return args -> {
			// Verificar si ya existe un almacén
			if (warehouseRepository.count() == 0) {
				Warehouse warehouse = new Warehouse();
				warehouse.setName("Almacén Principal");
				warehouse.setLocation("Ubicación desconocida"); // Puedes cambiar esta ubicación si lo deseas

				// Guardamos el almacén en la base de datos
				warehouseRepository.save(warehouse);
			}
		};
	}


}




