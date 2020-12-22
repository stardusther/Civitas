#encoding:utf-8

require_relative "./controlador.rb"

jugadores = []
jugadores.push (" MADRID ")
jugadores.push (" ROMA ")

vista = Civitas::Vista_textual.new()
juego = Civitas::CivitasJuego.new(jugadores)
Civitas::Dado.instance.setDebug(true)
controlador = Civitas::Controlador.new(juego, vista)

controlador.juega