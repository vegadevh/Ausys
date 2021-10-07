package com.digitalatmosphere.ausys.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.dto.CantidadCasosDTO;
import com.digitalatmosphere.ausys.dto.CasosDTO;
import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.HombresMujeresRangoFechaDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;
import com.digitalatmosphere.ausys.dto.RegistroDTO;
import com.digitalatmosphere.ausys.repositories.IDesaPeriRepo;

@Service
public class DesaPeriServiceImpl implements IDesaPeriService {
	
	@Autowired
	public IDesaPeriRepo desaPeriRepo;

	@Override
	public List<DesaPeri> findALL() throws DataAccessException {
		return desaPeriRepo.findAll();
	}

	@Override
	public void save(DesaPeri desaPeri) throws DataAccessException {
		desaPeriRepo.save(desaPeri);

	}

	@Override
	public DesaPeri findOne(Integer id_desaperi) throws DataAccessException {
		return desaPeriRepo.getById(id_desaperi);
	}
	
	EntityManager entityManager;
	
	public DesaPeriServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@Override
	@Transactional
	public Integer saveR(DesaPeri desaPeri) throws DataAccessException {
		entityManager.persist(desaPeri);
        return desaPeri.getId_desaperi();
	}

	@Override
	public List<DesaPeri> findAll() throws DataAccessException {
		return desaPeriRepo.findAll();
	}

	@Override
	public List<PeritajeDTO> findAllPeritajes() throws DataAccessException {
		List<PeritajeDTO> peritajes = desaPeriRepo.findAllPeritajes().stream().map(obj -> {
			PeritajeDTO p = new PeritajeDTO();
			p.setId_peritaje(obj[0].toString());
			p.setTipo_de_caso(obj[1].toString());
			p.setNombre(obj[2].toString());
			p.setApellido(obj[3].toString());
			p.setFecha_registro(obj[4].toString());
			p.setId_desaperi(obj[5].toString());
			return p;
		}).collect(Collectors.toList());
		return peritajes;
	}

	@Override
	public List<DesaparecidoDTO> findAllDesaparecidos() throws DataAccessException {
		List<DesaparecidoDTO> desaparecidos = desaPeriRepo.findAllDesaparecidos().stream().map(obj -> {
			DesaparecidoDTO d = new DesaparecidoDTO();
			d.setId_desaparecido(obj[0].toString());
			d.setTipo_de_caso(obj[1].toString());
			d.setNombre(obj[2].toString());
			d.setApellido(obj[3].toString());
			d.setFecha_registro(obj[4].toString());
			d.setId_desaperi(obj[5].toString());
			return d;
		}).collect(Collectors.toList());
		return desaparecidos;
	}

	@Override
	public void delete(Integer id_desaperi) throws DataAccessException {
		DesaPeri desaPeri = desaPeriRepo.getById(id_desaperi);
		desaPeriRepo.delete(desaPeri);
		
	}

	@Override
	public List<PeritajeDTO> buscarNombrePeritaje(String nombre) throws DataAccessException {
		List<PeritajeDTO> peritajes = desaPeriRepo.buscarNombrePeritaje(nombre).stream().map(obj -> {
			PeritajeDTO p = new PeritajeDTO();
			p.setId_peritaje(obj[0].toString());
			p.setTipo_de_caso(obj[1].toString());
			p.setNombre(obj[2].toString());
			p.setApellido(obj[3].toString());
			p.setFecha_registro(obj[4].toString());
			p.setId_desaperi(obj[5].toString());
			return p;
		}).collect(Collectors.toList());
		return peritajes;
	}

	@Override
	public List<PeritajeDTO> buscarIdPeritaje(String id) throws DataAccessException {
		List<PeritajeDTO> peritajes = desaPeriRepo.buscarIdPeritaje(id).stream().map(obj -> {
			PeritajeDTO p = new PeritajeDTO();
			p.setId_peritaje(obj[0].toString());
			p.setTipo_de_caso(obj[1].toString());
			p.setNombre(obj[2].toString());
			p.setApellido(obj[3].toString());
			p.setFecha_registro(obj[4].toString());
			p.setId_desaperi(obj[5].toString());
			return p;
		}).collect(Collectors.toList());
		return peritajes;
	}

	@Override
	public List<DesaparecidoDTO> buscarNombreDesaparecido(String nombre) throws DataAccessException {
		List<DesaparecidoDTO> peritajes = desaPeriRepo.buscarNombreDesaparecido(nombre).stream().map(obj -> {
			DesaparecidoDTO d = new DesaparecidoDTO();
			d.setId_desaparecido(obj[0].toString());
			d.setTipo_de_caso(obj[1].toString());
			d.setNombre(obj[2].toString());
			d.setApellido(obj[3].toString());
			d.setFecha_registro(obj[4].toString());
			d.setId_desaperi(obj[5].toString());
			return d;
		}).collect(Collectors.toList());
		return peritajes;
	}

	@Override
	public List<DesaparecidoDTO> buscarIdDesaparecido(String id) throws DataAccessException {
		List<DesaparecidoDTO> peritajes = desaPeriRepo.buscarIdDesaparecido(id).stream().map(obj -> {
			DesaparecidoDTO d = new DesaparecidoDTO();
			d.setId_desaparecido(obj[0].toString());
			d.setTipo_de_caso(obj[1].toString());
			d.setNombre(obj[2].toString());
			d.setApellido(obj[3].toString());
			d.setFecha_registro(obj[4].toString());
			d.setId_desaperi(obj[5].toString());
			return d;
		}).collect(Collectors.toList());
		return peritajes;
	}

	@Override
	public List<RegistroDTO> verRegistroPeritaje(String id_peritaje, String id_desaperi) throws DataAccessException {
		Integer desaperi = Integer.parseInt(id_desaperi);
		List<RegistroDTO> registro = desaPeriRepo.verRegistroPeritaje(id_peritaje, desaperi).stream().map(obj->{
			RegistroDTO r = new RegistroDTO();
			r.setId(obj[0].toString());
			r.setIdentificado(obj[1].toString());
			r.setEdad_estimada(obj[2].toString());
			r.setDivision(obj[3].toString());
			r.setDireccion(obj[4].toString());
			r.setTipo_de_caso(obj[5].toString());
			r.setNombre(obj[6].toString());
			r.setApellido(obj[7].toString());
			r.setSexo(obj[8].toString());
			r.setInformacion_adicional(obj[9].toString());
			r.setDui(obj[10].toString());
			r.setFecha_registro(obj[11].toString());
			r.setId_registro(obj[12].toString());
			return r;
		}).collect(Collectors.toList());
		return registro;
	}

	@Override
	public List<RegistroDTO> verRegistroDesaparecido(String id_desaparecido, String id_desaperi)
			throws DataAccessException {
		Integer desaperi = Integer.parseInt(id_desaperi);
		List<RegistroDTO> registro = desaPeriRepo.verRegistroDesaparecido(id_desaparecido, desaperi).stream().map(obj->{
			RegistroDTO r = new RegistroDTO();
			r.setId(obj[0].toString());
			r.setFecha_nacimiento(obj[1].toString());
			r.setNombre_familiar(obj[2].toString());
			r.setContacto_familiar(obj[3].toString());
			r.setDivision(obj[4].toString());
			r.setDireccion(obj[5].toString());
			r.setTipo_de_caso(obj[6].toString());
			r.setNombre(obj[7].toString());
			r.setApellido(obj[8].toString());
			r.setSexo(obj[9].toString());
			r.setInformacion_adicional(obj[10].toString());
			r.setDui(obj[11].toString());
			r.setFecha_registro(obj[12].toString());
			r.setId_registro(obj[13].toString());
			return r;
		}).collect(Collectors.toList());
		return registro;
	}

	@Override
	public List<DesaPeri> findByKeyword(String keyword, String sexo) throws DataAccessException {
		return desaPeriRepo.findByKeyword(keyword.toLowerCase(), sexo.toLowerCase());
	}
	
	@Override
	public List<DesaPeri> findByKeywordAndtipe(String keyword, String tipo, String sexo) throws DataAccessException {
		return desaPeriRepo.findByKeywordAndtipe(keyword.toLowerCase(), tipo, sexo.toLowerCase());
	}

	@Override
	public List<HombresMujeresRangoFechaDTO> HombresMujeresPorFecha(String inicio, String fin) throws DataAccessException {
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(inicio);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date1);
		System.out.println(date2);
		List<HombresMujeresRangoFechaDTO> rangoFechas = desaPeriRepo.HombresMujeresPorFecha(date1, date2).stream().map(obj -> {
			HombresMujeresRangoFechaDTO mh = new HombresMujeresRangoFechaDTO();
			mh.setMujer(obj[0].toString());
			mh.setHombre(obj[1].toString());
			return mh;
		}).collect(Collectors.toList());
		return rangoFechas;
	}

	@Override
	public List<CasosDTO> cantidadPorCasos() throws DataAccessException {
		List<CasosDTO> registro = desaPeriRepo.cantidadPorCasos().stream().map(obj->{
			CasosDTO c = new CasosDTO();
			c.setCaso1(obj[0].toString());
			c.setCaso2(obj[1].toString());
			c.setCaso3(obj[2].toString());
			c.setCaso4(obj[3].toString());
			c.setCaso5(obj[4].toString());
			return c;
		}).collect(Collectors.toList());
		return registro;
	}

	@Override
	public List<CasosDTO> cantidadPorCasosRango(String inicio, String fin) throws DataAccessException {
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(inicio);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<CasosDTO> registro = desaPeriRepo.cantidadPorCasosRango(date1, date2).stream().map(obj->{
			CasosDTO c = new CasosDTO();
			c.setCaso1(obj[0].toString());
			c.setCaso2(obj[1].toString());
			c.setCaso3(obj[2].toString());
			c.setCaso4(obj[3].toString());
			c.setCaso5(obj[4].toString());
			return c;
		}).collect(Collectors.toList());
		return registro;
	}
}
