<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/ProductoOrdenado">
        <LineaFactura>
            <ID_Item>
                <xsl:value-of select="@id"/>
            </ID_Item>
            <Referencia>
                <xsl:value-of select="sku"/>
            </Referencia>
            <Detalle>
                <xsl:value-of select="descripcion"/>
            </Detalle>
            <CantidadFacturada>
                <xsl:value-of select="qty"/>
            </CantidadFacturada>
            <PrecioTotal>
                <xsl:value-of select="qty  unitPrice"/>
            </PrecioTotal>
        </LineaFactura>
    </xsl:template>

</xsl:stylesheet>