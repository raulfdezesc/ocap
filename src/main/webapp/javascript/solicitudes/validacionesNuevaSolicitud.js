function validarFormulario(formu, validarRadioProcedimiento) {
   
   // PRIMER APELLIDO
   if (validarRadioProcedimiento == '') {      
      var cadena = formu.DApellido1.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
         return false;
      } else {
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
            return false;
         } else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido1)){
               formu.DApellido1.value = cadena;
            } else{
               alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
   } else {
      var cadena = formu.DApellido1.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
         return false;
      } else {
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
            return false;
         } else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido1)){
               formu.DApellido1.value = cadena;
            } else{
               alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
      
      /*
      var cadena = formu.DApellido2.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Segundo Apellido\' del apartado de Datos Personales.');
         return false;
      } else {
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Segundo Apellido\' del apartado de Datos Personales.');
            return false;
         } else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido2)){
               formu.DApellido2.value = cadena;
            } else{
               alert('El campo \'Segundo Apellido\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
      */
   }
   
   // NOMBRE
   var cadena = formu.DNombre.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Nombre\' del apartado de Datos Personales.');
      return false;
   } else {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Nombre\' del apartado de Datos Personales.');
         return false;
      } else {
         if(LetrasYNumeros(formu.DNombre)){
            formu.DNombre.value = cadena;
         } else{
            alert('El campo \'Nombre\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }

   // NIF/NIE
   var cadena = formu.CDniReal.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'NIF/NIE\' del apartado de Datos Personales.');
      return false;
   } else {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'NIF/NIE\' del apartado de Datos Personales.');
         return false;
      } else {
         if(validar_nif(cadena.toUpperCase()) != 0){
            formu.CDniReal.value = cadena;
         } else{
            alert('El campo \'NIF/NIE\' es err\u00f3neo.');
            return false;
         }
      }
   }
   
   // SEXO
   if(formu.BSexo.value == ''){
      alert('Debe rellenar el campo \'Sexo\' del apartado de Datos Personales.');
      return false;
   }   

   // TELÉFONO 1
   var cadena = formu.NTelefono1.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Tel\u00e9fono 1\' del apartado de Datos Personales.');
      return false;
   } else {
	   cadena = trim(cadena);
	   if(cadena!='') {
	         if(telefonos(formu.NTelefono1)){
	            formu.NTelefono1.value = cadena;
	         } else{
	            alert('El campo \'Tel\u00e9fono 1\' tiene un formato err\u00f3neo. Debe ser num\u00e9rico de 9 d\u00edgitos.');
	            return false;
	         }
	   }
   }

   // TELÉFONO 2
   var cadena = formu.NTelefono2.value;
   cadena = trim(cadena);
   if(cadena!='') {
         if(telefonos(formu.NTelefono2)){
            formu.NTelefono2.value = cadena;
         } else{
            alert('El campo \'Tel\u00e9fono 2\' tiene un formato err\u00f3neo. Debe ser num\u00e9rico de 9 d\u00edgitos.');
            return false;
         }
   }

   // CORREO ELECTRÓNICO
   var cadena = formu.DCorreoelectronico.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Correo Electr\u00f3nico\' del apartado de Datos Personales.');
      return false;
   } else {
	   var cadena = formu.DCorreoelectronico.value;
	   cadena = trim(cadena);
	      
	   if (cadena != '') {
	            if(esDireccionCorreo(formu.DCorreoelectronico)){
	               formu.DCorreoelectronico.value = cadena;
	            } else{
	               alert('El campo \'Correo Electr\u00f3nico\' tiene un formato err\u00f3neo.');
	               return false;
	            }
	   }
   }
   
   // DOMICILIO
   var cadena = formu.DDomicilio.value;
   cadena = trim(cadena);
   if(cadena=='') {
      alert('Debe rellenar el campo \'Domicilio\' del apartado de Datos Personales.');
      return false;
   }
   if(LetrasYNumeros(formu.DDomicilio)){
      formu.DDomicilio.value = cadena;
   } else{
      alert('El campo \'Domicilio\' contiene caracteres no permitidos.');
      return false;
   }
   
   //PROVINCIA
   if(formu.CProvincia_id.value == ''){
      alert('Debe rellenar el campo \'Provincia\' del apartado de Datos Personales.');
      return false;
   } 
   
      
   //LOCALIDAD
   if(formu.CLocalidad_id.value == ''){
      alert('Debe rellenar el campo \'Localidad\' del apartado de Datos Personales.');
      return false;
   } 
   
   // CODIGO POSTAL
   var cadena = formu.CPostalUsu.value;
   cadena = trim(cadena);
   if (cadena != '') {
      if (cadena.length  < 4 || cadena.length > 5 ) {
         alert('El campo \'C\u00f3digo Postal\' tiene un formato err\u00f3neo. Deben estar formado por 5 d\u00edgitos.');
         return false;
      } else {
         if(soloNumeros(formu.CPostalUsu)){
            if (cadena.length==4)
               cadena = '0'+cadena;
               if (formu.CProvincia_id.value !='' && formu.CProvincia_id.value != cadena.substring(0,2)) {
                  alert('El campo \'C\u00f3digo Postal\' no pertenece a la provincia seleccionada.');
                  return false;
               }
            formu.CPostalUsu.value = cadena;
         } else{
            alert('El campo \'C\u00f3digo Postal\' tiene un formato err\u00f3neo. Debe estar formado por 5 d\u00edgitos.');
            return false;
         }
      }
   } else {
      alert('Debe rellenar el campo \'C\u00F3digo Postal\' del apartado de Datos Personales.');
      return false;
   }

   //FECHA REGISTRO OFICIAL
   /*if (validarRadioProcedimiento=='') {   
      var cadena = formu.FRegistro_oficial.value;
      cadena = trim(cadena);
      if(cadena == ''){
         alert("Debe rellenar el campo \" Fecha de Registro Oficial \".");
         return false;
      }
      //Si no introduce la hora, ponemos por defecto las 00:00:00
      if (cadena.length < 19 && cadena.length >= 10) {
         cadena = cadena.substring(0,10)+' 00:00:00';
         formu.FRegistro_oficial.value = cadena;
      }
      if (!(esTimestampCorrecto(cadena))){
         alert("Formato de \" Fecha de Registro Oficial \" no es correcto.");
         return false;
      }
      if(comprobarFechaNoFutura(cadena)){
         alert("La \" Fecha de Registro Oficial \" no es correcta.");
         return false;
      } 
      if(esFechaHora1MayorQueFechaHora2(cadena, cadena.substr(11,8), formu.FRegistro_solic.value, formu.FRegistro_solic.value.substr(11,8))){
         alert("La \" Fecha de Registro Oficial \" no es correcta.");
         return false;
      }
   }*/

   // REGIMEN JURIDICO
   if(formu.CJuridicoId.value == ''){
      alert('Debe rellenar el campo \'R\u00e9gimen Jur\u00eddico\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   if(formu.CJuridicoId.value=='1' && formu.CJuridicoCombo.value==''){
      alert('Debe seleccionar un \'Tipo\' de Estatutario Fijo para el campo \'R\u00e9gimen Jur\u00eddico\'.');
      return false;
   }

   if(formu.CJuridicoId.value == '3' && formu.DRegimenJuridicoOtros.value==''){
      alert('Debe rellenar el campo de texto al seleccionar la opci\u00f3n \'Otros\' en \'R\u00e9gimen Jur\u00eddico\'.');
      return false;
   }
   
   // CONVOCATORIA
     if( formu.CConvocatoriaId.value == '-1' ){
      alert('Debe rellenar el campo \'Convocatoria\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   
   // CATEGORÍA PROFESIONAL
   if(formu.CJuridicoId.value!='1' && formu.CProfesional_id.value==''){
      alert('Debe rellenar el campo \'Categor\u00EDa\' de los Datos por los que opta a la Carrera Profesional.');
      return false;   
   }

   // CATEGORÍA PROFESIONAL FIJO
   if(formu.CJuridicoId.value=='1' && formu.CProfesionalFijo_id.value==''){
      alert('Debe rellenar el campo \'Categor\u00EDa\' de los Datos por los que opta a la Carrera Profesional.');
      return false;   
   }
   
   // ESPECIALIDAD
   if(formu.CJuridicoId.value!='1' && formu.CEspec_id.value == '' && !formu.CEspec_id.disabled){
      alert('Debe rellenar el campo \'Especialidad\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   // ESPECIALIDAD FIJO
   if(formu.CJuridicoId.value=='1' && formu.CEspecFijo_id.value == '' && !formu.CEspecFijo_id.disabled){
      alert('Debe rellenar el campo \'Especialidad\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   // GRADO AL QUE OPTA
   if(formu.CGrado_id != null){
      if(formu.CGrado_id.value == ''){
         alert('Debe rellenar el campo \'Grado al que opta\' de los Datos por los que opta a la Carrera Profesional.');
         return false;
      }
   }
   
   // PROCEDIMIENTO DE EVALUCION (PGP)
   if(formu.CProcedimientoId.value == ''){
      alert('Debe rellenar el campo \'Procedimiento de evaluaci\u00f3n por el que opta\' de los Datos de la Carrera Profesional.');
      return false;
   }
   
   // SITUACION ADMINISTRATIVA
   if(formu.CSitAdministrativaId.value == ''){
      alert('Debe rellenar el campo \'Situaci\u00f3n Administrativa\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }
   
   if(formu.CSitAdministrativaId.value == '6' && formu.DSitAdministrativaOtros.value==''){
      alert('Debe rellenar el campo \'Otras Situaciones Administrativas\'.');
      return false;
   }

   // PROVINCIA CARRERA
   if(formu.CProvinciaCarrera_id.value == ''){
      alert('Debe rellenar el campo \'Provincia\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   // GERENCIA CARRERA
   if(formu.CGerencia_id.value == ''){
      alert('Debe rellenar el campo \'Gerencia\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }   

   // CENTRO TRABAJO CARRERA
   if(formu.CCentrotrabajo_id.value == ''){
      alert('Debe rellenar el campo \'Centro de Trabajo\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   // SERVICIO CARRERA
   /*
   if(formu.DServicio.value == '') {
      alert('Debe rellenar el campo \'Servicio\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   } else {
   */
   if(formu.DServicio.value != '') {
      var cadena = formu.DServicio.value;
      cadena = trim(cadena);
      if(LetrasYNumeros(formu.DServicio)){
         formu.DServicio.value = cadena;
      } else{
         alert('El campo \'Servicio\' contiene caracteres no permitidos.');
         return false;
      }   
   }

   // AREA/UNIDAD/PUESTO
   var cadena = formu.DPuesto.value;
   cadena = trim(cadena);
   if(LetrasYNumeros(formu.DPuesto)){
      formu.DPuesto.value = cadena;
   } else{
      alert('El campo \'Puesto\' contiene caracteres no permitidos.');
      return false;
   }
   
   // Validacion de nuevos campos para reflejar la situacion actual, a fecha de convocatoria:
   // SITUACION ADMINISTRATIVA ACTUAL
   if(formu.CSitAdmonActualId.value == ''){
      alert('Debe rellenar el campo \'Situaci\u00f3n Administrativa\' de los Datos Profesionales a Fecha de Convocatoria.');
      return false;
   }
   
   if(formu.CSitAdmonActualId.value == '6' && formu.AOtraSitAdmonActual.value==''){
      alert('Debe rellenar el campo \'Otras Situaciones Administrativas\' de los Datos Profesionales a Fecha de Convocatoria.');
      return false;
   }
   
   // PROVINCIA CARRERA ACTUAL
   if(formu.CProvCarreraActualId.value == ''){
      alert('Debe rellenar el campo \'Provincia\' de los Datos Profesionales a Fecha de Convocatoria.');
      return false;
   }

   // GERENCIA CARRERA ACTUAL
   if(formu.CGerenciaActualId.value == ''){
      alert('Debe rellenar el campo \'Gerencia\' de los Datos Profesionales a Fecha de Convocatoria.');
      return false;
   }   

   // CENTRO TRABAJO CARRERA ACTUAL
   if(formu.CCentroTrabActualId.value == ''){
      alert('Debe rellenar el campo \'Centro de Trabajo\' de los Datos Profesionales a Fecha de Convocatoria.');
      return false;
   }
   
   // AÑOS ANTIGUEDAD
   var cadena = formu.NAniosantiguedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
      alert('Debe rellenar el campo \'N\u00famero de a\u00f1os\'.');
      return false;
   } else {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'N\u00famero de a\u00f1os\'.');
         return false;
      }
      else {
         if(!soloNumeros(document.forms[0].NAniosantiguedad)){
            alert('El campo \'N\u00famero de a\u00f1os\' es err\u00f3neo.');
            return false;
         }
      }
   }
   
   // MESES ANTIGUEDAD
   var cadena = formu.NMesesantiguedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
    formu.NMesesantiguedad.value = '0';
    cadena = '0';
   }
   if(!soloNumeros(formu.NMesesantiguedad)){
      alert('El campo \'N\u00famero de meses\' es err\u00f3neo.');
      return false;
   } else if (parseInt(cadena) > 11) {
      alert('El campo \'N\u00famero de meses\' no puede ser mayor o igual a 12.');
      return false;
   }

   // DIAS ANTIGUEDAD
   var cadena = formu.NDiasantiguedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
    formu.NDiasantiguedad.value = '0';
    cadena = '0';
   }
   if(!soloNumeros(formu.NDiasantiguedad)){
      alert('El campo \'N\u00famero de d\u00edas\' es err\u00f3neo.');
      return false;
   } else if (parseInt(cadena) > 30) {
      alert('El campo \'N\u00famero de d\u00edas\' no puede ser mayor que 30.');
      return false;
   }
   
   // GRADO QUE POSSE
   if(formu.CModAnteriorId.value == ''){
      alert('Debe rellenar el campo \'Grado que posee\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }
   
   // B_LOPD_SOLICITUD
   if(formu.BLopdSolicitud){
      if(formu.BLopdSolicitud[0].checked==false && formu.BLopdSolicitud[1].checked==false){
         alert('Debe indicar si acepta o no el Consentimiento para guardar los datos aportados.');
         return false;
      }   
   }
   
   return true;
}

function validarFormularioModif(formu, validarRadioProcedimiento) {
   
   // PRIMER APELLIDO
   if (validarRadioProcedimiento == '') {
      var cadena = formu.DApellido1.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
         return false;
      } else {
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
            return false;
         } else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido1)){
               formu.DApellido1.value = cadena;
            } else{
               alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
   } else {
      var cadena = formu.DApellido1.value;
      if(cadena=='') {
         alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
         return false;
      } else {
         cadena = trim(cadena);
         if(cadena != null && cadena =='') {
            alert('Debe rellenar el campo \'Apellidos\' del apartado de Datos Personales.');
            return false;
         } else if(cadena != null && cadena !='') {
            if(LetrasYNumeros(formu.DApellido1)){
               formu.DApellido1.value = cadena;
            } else{
               alert('El campo \'Apellidos\' contiene caracteres no permitidos.');
               return false;
            }
         }
      }
   }
   
   // NOMBRE
   var cadena = formu.DNombre.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Nombre\' del apartado de Datos Personales.');
      return false;
   } else {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'Nombre\' del apartado de Datos Personales.');
         return false;
      } else {
         if(LetrasYNumeros(formu.DNombre)){
            formu.DNombre.value = cadena;
         } else{
            alert('El campo \'Nombre\' contiene caracteres no permitidos.');
            return false;
         }
      }
   }
   
   // NIF/NIE
   var cadena = formu.CDniReal.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'NIF/NIE\' del apartado de Datos Personales.');
      return false;
   } else {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'NIF/NIE\' del apartado de Datos Personales.');
         return false;
      } else {
         if(validar_nif(cadena.toUpperCase()) != 0){
            formu.CDniReal.value = cadena;
         } else{
            alert('El campo \'NIF/NIE\' es err\u00f3neo.');
            return false;
         }
      }
   }
   
   // SEXO
   if(formu.BSexo.value == ''){
      alert('Debe rellenar el campo \'Sexo\' del apartado de Datos Personales.');
      return false;
   }   
   
   // TELÉFONO 1
   var cadena = formu.NTelefono1.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Tel\u00e9fono 1\' del apartado de Datos Personales.');
      return false;
   } else {
	   cadena = trim(cadena);
	   if(cadena!='') {
	         if(telefonos(formu.NTelefono1)){
	            formu.NTelefono1.value = cadena;
	         } else{
	            alert('El campo \'Tel\u00e9fono 1\' tiene un formato err\u00f3neo. Debe ser num\u00e9rico de 9 d\u00edgitos.');
	            return false;
	         }
	   }
   }
   
   // TELÉFONO 2
   var cadena = formu.NTelefono2.value;
   cadena = trim(cadena);
   if(cadena!='') {
      if(telefonos(formu.NTelefono2)){
         formu.NTelefono2.value = cadena;
      } else{
         alert('El campo \'Tel\u00e9fono 2\' tiene un formato err\u00f3neo. Debe ser num\u00e9rico de 9 d\u00edgitos.');
         return false;
      }
   }
   
   // CORREO ELECTRÓNICO
   var cadena = formu.DCorreoelectronico.value;
   if(cadena=='') {
      alert('Debe rellenar el campo \'Correo Electr\u00f3nico\' del apartado de Datos Personales.');
      return false;
   } else {
	   var cadena = formu.DCorreoelectronico.value;
	   cadena = trim(cadena);
	      
	   if (cadena != '') {
	            if(esDireccionCorreo(formu.DCorreoelectronico)){
	               formu.DCorreoelectronico.value = cadena;
	            } else{
	               alert('El campo \'Correo Electr\u00f3nico\' tiene un formato err\u00f3neo.');
	               return false;
	            }
	   }
   }
   
   // DOMICILIO
   var cadena = formu.DDomicilio.value;
   cadena = trim(cadena);
   if(cadena=='') {
      alert('Debe rellenar el campo \'Domicilio\' del apartado de Datos Personales.');
      return false;
   }
   if(LetrasYNumeros(formu.DDomicilio)){
      formu.DDomicilio.value = cadena;
   } else{
      alert('El campo \'Domicilio\' contiene caracteres no permitidos.');
      return false;
   }
   
   //PROVINCIA
   if(formu.CProvincia_id.value == ''){
      alert('Debe rellenar el campo \'Provincia\' del apartado de Datos Personales.');
      return false;
   } 
   
   //LOCALIDAD
   if(formu.CLocalidad_id.value == ''){
      alert('Debe rellenar el campo \'Localidad\' del apartado de Datos Personales.');
      return false;
   } 
   
   // CODIGO POSTAL
   var cadena = formu.CPostalUsu.value;
   cadena = trim(cadena);
   if (cadena != '') {
      if (cadena.length  < 4 || cadena.length > 5 ) {
         alert('El campo \'C\u00f3digo Postal\' tiene un formato err\u00f3neo. Deben estar formado por 5 d\u00edgitos.');
         return false;
      } else {
         if(soloNumeros(formu.CPostalUsu)){
            if (cadena.length==4)
               cadena = '0'+cadena;
               if (formu.CProvincia_id.value !='' && formu.CProvincia_id.value != cadena.substring(0,2)) {
                  alert('El campo \'C\u00f3digo Postal\' no pertenece a la provincia seleccionada.');
                  return false;
               }
            formu.CPostalUsu.value = cadena;
         } else{
            alert('El campo \'C\u00f3digo Postal\' tiene un formato err\u00f3neo. Debe estar formado por 5 d\u00edgitos.');
            return false;
         }
      }
   } else {
      alert('Debe rellenar el campo \'C\u00F3digo Postal\' del apartado de Datos Personales.');
      return false;
   }

   // REGIMEN JURIDICO
   if(formu.CJuridicoId.value == ''){
      alert('Debe rellenar el campo \'R\u00e9gimen Jur\u00eddico\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   if(formu.CJuridicoId.value=='1' && formu.CJuridicoCombo.value==''){
      alert('Debe seleccionar un \'Tipo\' de Estatutario Fijo para el campo \'R\u00e9gimen Jur\u00eddico\'.');
      return false;
   }

   if(formu.CJuridicoId.value == '3' && formu.DRegimenJuridicoOtros.value==''){
      alert('Debe rellenar el campo de texto al seleccionar la opci\u00f3n \'Otros\' en \'R\u00e9gimen Jur\u00eddico\'.');
      return false;
   }
   
   // CATEGORÍA PROFESIONAL
   if(formu.CJuridicoId.value!='1' && formu.CProfesional_id.value==''){
      alert('Debe rellenar el campo \'Categor\u00EDa\' de los Datos por los que opta a la Carrera Profesional.');
      return false;   
   }
   
   // CATEGORÍA PROFESIONAL FIJO
   if(formu.CJuridicoId.value=='1' && formu.CProfesionalFijo_id.value==''){
      alert('Debe rellenar el campo \'Categor\u00EDa\' de los Datos por los que opta a la Carrera Profesional.');
      return false;   
   }
   
   // ESPECIALIDAD
   if(formu.CJuridicoId.value!='1' && formu.CEspec_id.value == '' && !formu.CEspec_id.disabled){
      alert('Debe rellenar el campo \'Especialidad\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }
   
   // ESPECIALIDAD FIJO
   if(formu.CJuridicoId.value=='1' && formu.CEspecFijo_id.value == '' && !formu.CEspecFijo_id.disabled){
      alert('Debe rellenar el campo \'Especialidad\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }
   
   // GRADO AL QUE OPTA
   if(formu.CGrado_id != null){
      if(formu.CGrado_id.value == ''){
         alert('Debe rellenar el campo \'Grado al que opta\' de los Datos por los que opta a la Carrera Profesional.');
         return false;
      }
   }
   
   // PROCEDIMIENTO DE EVALUCION (PGP)
   if(formu.CProcedimientoId.value == ''){
      alert('Debe rellenar el campo \'Procedimiento de evaluaci\u00f3n por el que opta\' de los Datos de la Carrera Profesional.');
      return false;
   }
   
   // SITUACION ADMINISTRATIVA
   if(formu.CSitAdministrativaId.value == ''){
      alert('Debe rellenar el campo \'Situaci\u00f3n Administrativa\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }
   
   if(formu.CSitAdministrativaId.value == '6' && formu.DSitAdministrativaOtros.value==''){
      alert('Debe rellenar el campo \'Otras Situaciones Administrativas\'.');
      return false;
   }

   // PROVINCIA CARRERA
   if(formu.CProvinciaCarrera_id.value == ''){
      alert('Debe rellenar el campo \'Provincia\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }

   // GERENCIA CARRERA
   if(formu.CGerencia_id.value == ''){
      alert('Debe rellenar el campo \'Gerencia\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }   

   // CENTRO TRABAJO CARRERA
   if(formu.CCentrotrabajo_id.value == ''){
      alert('Debe rellenar el campo \'Centro de Trabajo\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }
   
   // PROVINCIA CARRERA ACTUAL
   if(formu.CProvCarreraActualId != undefined){
	   if(formu.CProvCarreraActualId.value == ''){
		   alert('Debe rellenar el campo \'Provincia\' del apartado 2.2 de los Datos por los que opta a la Carrera Profesional.');
		   return false;
	   }
   }

   // GERENCIA CARRERA ACTUAL
   if(formu.CGerenciaActualId != undefined){
	   if(formu.CGerenciaActualId.value == ''){
		   alert('Debe rellenar el campo \'Gerencia\' del apartado 2.2  de los Datos por los que opta a la Carrera Profesional.');
		   return false;
	   }
   }   

   // CENTRO TRABAJO CARRERA ACTUAL
   if(formu.CCentroTrabActualId != undefined){
	   if(formu.CCentroTrabActualId.value == ''){
	   		alert('Debe rellenar el campo \'Centro de Trabajo\' del apartado 2.2  de los Datos por los que opta a la Carrera Profesional.');
	   		return false;
   		}
   }

   if(formu.DServicio.value != '') {
      var cadena = formu.DServicio.value;
      cadena = trim(cadena);
      if(LetrasYNumeros(formu.DServicio)){
         formu.DServicio.value = cadena;
      } else{
         alert('El campo \'Servicio\' contiene caracteres no permitidos.');
         return false;
      }   
   }

   // AREA/UNIDAD/PUESTO
   var cadena = formu.DPuesto.value;
   cadena = trim(cadena);
   if(LetrasYNumeros(formu.DPuesto)){
      formu.DPuesto.value = cadena;
   } else{
      alert('El campo \'Puesto\' contiene caracteres no permitidos.');
      return false;
   }
   
   // AÑOS ANTIGUEDAD
   var cadena = formu.NAniosantiguedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
      alert('Debe rellenar el campo \'N\u00famero de a\u00f1os\'.');
      return false;
   } else {
      cadena = trim(cadena);
      if(cadena=='') {
         alert('Debe rellenar el campo \'N\u00famero de a\u00f1os\'.');
         return false;
      }
      else {
         if(!soloNumeros(document.forms[0].NAniosantiguedad)){
            alert('El campo \'N\u00famero de a\u00f1os\' es err\u00f3neo.');
            return false;
         }
      }
   }
   
   // MESES ANTIGUEDAD
   var cadena = formu.NMesesantiguedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
    formu.NMesesantiguedad.value = '0';
    cadena = '0';
   }
   if(!soloNumeros(formu.NMesesantiguedad)){
      alert('El campo \'N\u00famero de meses\' es err\u00f3neo.');
      return false;
   } else if (parseInt(cadena) > 11) {
      alert('El campo \'N\u00famero de meses\' no puede ser mayor o igual a 12.');
      return false;
   }

   // DIAS ANTIGUEDAD
   var cadena = formu.NDiasantiguedad.value;
   cadena = trim(cadena);
   if(cadena=='') {
    formu.NDiasantiguedad.value = '0';
    cadena = '0';
   }
   if(!soloNumeros(formu.NDiasantiguedad)){
      alert('El campo \'N\u00famero de d\u00edas\' es err\u00f3neo.');
      return false;
   } else if (parseInt(cadena) > 30) {
      alert('El campo \'N\u00famero de d\u00edas\' no puede ser mayor que 30.');
      return false;
   }
   
   // GRADO QUE POSSE
   if(formu.CModAnteriorId.value == ''){
      alert('Debe rellenar el campo \'Grado que posee\' de los Datos por los que opta a la Carrera Profesional.');
      return false;
   }
   
   return true;
   
}
