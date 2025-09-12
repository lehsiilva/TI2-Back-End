package com.exemplo.service;

import static spark.Spark.*;

import com.exemplo.dao.ProdutoDAO;
import com.exemplo.model.Produto;

import spark.Request;
import spark.Response;

public class ProdutoService {

    private static ProdutoDAO produtoDAO = new ProdutoDAO();

    public static void main(String[] args) {

        port(4567); // define a porta
        staticFiles.location("/public"); // arquivos HTML em resources/public

        // MENU INICIAL
        get("/", (req, res) -> {
            return "<h1>Menu Inicial</h1>" +
                   "<ul>" +
                   "<li><a href='/produtos'>Listar Produtos</a></li>" +
                   "<li><a href='/form.html'>Cadastrar Produto</a></li>" +
                   "</ul>";
        });

        // LISTAR TODOS OS PRODUTOS
        get("/produtos", (req, res) -> {
            StringBuilder sb = new StringBuilder("<h1>Lista de Produtos</h1><ul>");
            for (Produto p : produtoDAO.listar()) {
                sb.append("<li>")
                  .append(p.getId()).append(" - ")
                  .append(p.getNome()).append(" - R$")
                  .append(p.getPreco())
                  .append(" <form action='/produto/deletar' method='post' style='display:inline;'>")
                  .append("<input type='hidden' name='id' value='").append(p.getId()).append("'>")
                  .append("<input type='submit' value='Excluir'>")
                  .append("</form>")
                  .append("</li>");
            }
            sb.append("</ul>");
            sb.append("<a href='/'>Voltar ao Menu</a>");
            return sb.toString();
        });

        // PEGAR PRODUTO POR ID (retorna XML)
        get("/produto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Produto p = produtoDAO.listar().stream()
                                  .filter(prod -> prod.getId() == id)
                                  .findFirst()
                                  .orElse(null);
            if (p != null) {
                res.type("application/xml");
                return "<produto>\n" +
                       "\t<id>" + p.getId() + "</id>\n" +
                       "\t<nome>" + p.getNome() + "</nome>\n" +
                       "\t<preco>" + p.getPreco() + "</preco>\n" +
                       "</produto>";
            } else {
                res.status(404);
                return "Produto não encontrado";
            }
        });

        // ADICIONAR PRODUTO
        post("/produto/inserir", (req, res) -> {
            String nome = req.queryParams("nome");
            String precoStr = req.queryParams("preco").replace(",", ".");
            double preco = Double.parseDouble(precoStr);
            produtoDAO.inserir(new Produto(0, nome, preco));
            res.redirect("/produtos");
            return null;
        });

        // DELETAR PRODUTO
        post("/produto/deletar", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            produtoDAO.excluir(id);
            res.redirect("/produtos");
            return null;
        });
    }
}
