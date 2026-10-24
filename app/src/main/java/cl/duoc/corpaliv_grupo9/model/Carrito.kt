package cl.duoc.corpaliv_grupo9.model

data class ItemCarrito (
    val producto: Producto,
    val cantidad: Int
){
    val subtotal: Int
        get()=producto.valor*cantidad
}
fun totalCarrito(items: List<ItemCarrito>): Int{
    return items.sumOf {it.subtotal}
}
// aca se convierto los 10990 en "$10.990"
fun formatearPesos(valor:Int): String{
    return "$"+ String.format("%,d",valor).replace(",", ".")
}