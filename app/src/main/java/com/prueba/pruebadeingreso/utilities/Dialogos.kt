package com.prueba.pruebadeingreso.utilities

import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog

object Dialogos {

    fun mostrarDialogoProgress(
        contexto: Context?,
        titulo: String?,
    ): SweetAlertDialog? {
        val dialog = SweetAlertDialog(contexto, SweetAlertDialog.PROGRESS_TYPE)
        dialog.setTitleText(titulo)
        return dialog
    }

}